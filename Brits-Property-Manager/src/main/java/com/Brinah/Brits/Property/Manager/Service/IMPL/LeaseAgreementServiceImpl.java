package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementResponseDto;
import com.Brinah.Brits.Property.Manager.Entities.*;
import com.Brinah.Brits.Property.Manager.Enums.LeaseStatus;
import com.Brinah.Brits.Property.Manager.Repositories.*;
import com.Brinah.Brits.Property.Manager.Service.Interface.LeaseAgreementService;
import com.Brinah.Brits.Property.Manager.Utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaseAgreementServiceImpl implements LeaseAgreementService {

    private final LeaseAgreementRepository leaseAgreementRepository;
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;
    private final UnitRepository unitRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public LeaseAgreementResponseDto createLeaseAgreement(LeaseAgreementRequestDto request) {
        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Pick the first available unit if the property has units
        Unit unit = property.getUnits() != null && !property.getUnits().isEmpty()
                ? property.getUnits().iterator().next()
                : null;

        LeaseAgreement lease = LeaseAgreement.builder()
                .referenceCode(generateReferenceCode())
                .tenant(tenant)
                .property(property)
                .unit(unit)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .monthlyRent(request.getRentAmount())
                .deposit(request.getRentAmount() * 2) // example business rule
                .terms(request.getTerms())
                .status(LeaseStatus.ACTIVE)
                .build();

        LeaseAgreement saved = leaseAgreementRepository.save(lease);
        return mapToResponse(saved);
    }

    @Override
    public LeaseAgreementResponseDto updateLeaseAgreement(Long id, LeaseAgreementRequestDto request) {
        LeaseAgreement lease = leaseAgreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Agreement not found"));

        if (request.getStartDate() != null) lease.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) lease.setEndDate(request.getEndDate());
        if (request.getRentAmount() != null) lease.setMonthlyRent(request.getRentAmount());
        if (request.getTerms() != null) lease.setTerms(request.getTerms());

        LeaseAgreement updated = leaseAgreementRepository.save(lease);
        return mapToResponse(updated);
    }

    @Override
    public LeaseAgreementResponseDto getLeaseAgreementById(Long id) {
        LeaseAgreement lease = leaseAgreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Agreement not found"));
        return mapToResponse(lease);
    }

    @Override
    public List<LeaseAgreementResponseDto> getAllLeaseAgreements() {
        return leaseAgreementRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaseAgreementResponseDto> getLeaseAgreementsByTenant(Long tenantId) {
        return leaseAgreementRepository.findByTenantId(tenantId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLeaseAgreement(Long id) {
        LeaseAgreement lease = leaseAgreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Agreement not found"));
        leaseAgreementRepository.delete(lease);
    }

    // ---------- Utility Methods ----------

    private LeaseAgreementResponseDto mapToResponse(LeaseAgreement lease) {
        LeaseAgreementResponseDto dto = modelMapperUtil.mapToDto(lease, LeaseAgreementResponseDto.class);

        dto.setTenantName(
                Optional.ofNullable(lease.getTenant())
                        .map(Tenant::getTenantName)
                        .orElse(null)
        );

        dto.setPropertyName(
                Optional.ofNullable(lease.getProperty())
                        .map(Property::getPropertyName)
                        .orElse(null)
        );

        dto.setRentAmount(lease.getMonthlyRent());
        return dto;
    }

    private String generateReferenceCode() {
        return "LEASE-" + LocalDate.now() + "-" + System.currentTimeMillis();
    }
}
