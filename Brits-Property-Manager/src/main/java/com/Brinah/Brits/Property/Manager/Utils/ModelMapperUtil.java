package com.Brinah.Brits.Property.Manager.Utils;

import com.Brinah.Brits.Property.Manager.DTO.*;
import com.Brinah.Brits.Property.Manager.DTO.Auth.*;
import com.Brinah.Brits.Property.Manager.Entities.*;
import com.Brinah.Brits.Property.Manager.Enums.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public ModelMapperUtil() {
        this.modelMapper = new ModelMapper();
    }

    // ===================== GENERIC MAPPERS =====================

    /**
     * Generic method to map any entity to a target DTO class.
     */
    public <D, T> D mapToDto(final T entity, Class<D> outClass) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, outClass);
    }

    /**
     * Generic method to map any DTO to a target Entity class.
     */
    public <D, T> T mapToEntity(final D dto, Class<T> outClass) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, outClass);
    }

    // ===================== USER =====================
    public UserResponseDto mapToUserResponse(User user) {
        if (user == null) return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole().name());
        dto.setActive(user.isEnabled());
        return dto;
    }

    public User mapRegisterRequestToUser(RegisterRequest req) {
        if (req == null) return null;

        return User.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .role(req.getRole())
                .fullName(req.getFullName())
                .idNumber(req.getIdNumber())
                .phoneNumber(req.getPhoneNumber())
                .address(req.getAddress())
                .gender(req.getGender())
                .profilePicture(req.getProfilePicture())
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactPhone(req.getEmergencyContactPhone())
                .enabled(true)
                .build();
    }

    // ===================== PROPERTY =====================
    public Property mapPropertyRequestToEntity(PropertyRequestDto dto, User landlord) {
        if (dto == null) return null;

        Property property = new Property();
        property.setPropertyName(dto.getPropertyName());
        property.setLocation(dto.getLocation());
        property.setType(dto.getType());
        property.setDescription(dto.getDescription());
        property.setLandlord(landlord);
        property.setStatus(PropertyStatus.ACTIVE);
        return property;
    }

    public PropertyResponseDto mapPropertyToResponse(Property entity) {
        if (entity == null) return null;

        PropertyResponseDto dto = new PropertyResponseDto();
        dto.setId(entity.getId());
        dto.setPropertyName(entity.getPropertyName());
        dto.setLocation(entity.getLocation());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus() : PropertyStatus.ACTIVE);
        dto.setLandlordName(entity.getLandlord() != null ? entity.getLandlord().getFullName() : null);

        if (entity.getUnits() != null) {
            dto.setUnits(entity.getUnits().stream()
                    .map(this::mapUnitToResponse)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // ===================== UNIT =====================
    public Unit mapUnitRequestToEntity(UnitRequestDto dto, Property property) {
        if (dto == null) return null;

        Unit unit = new Unit();
        unit.setUnitNumber(dto.getUnitNumber());
        unit.setRentAmount(dto.getRentAmount());
        unit.setDepositAmount(dto.getDepositAmount());
        unit.setSize(dto.getSize());
        unit.setDescription(dto.getDescription());
        unit.setStatus(UnitStatus.valueOf(dto.getStatus()));
        unit.setProperty(property);
        return unit;
    }

    public UnitResponseDto mapUnitToResponse(Unit entity) {
        if (entity == null) return null;

        UnitResponseDto dto = new UnitResponseDto();
        dto.setId(entity.getId());
        dto.setUnitNumber(entity.getUnitNumber());
        dto.setRentAmount(entity.getRentAmount());
        dto.setDepositAmount(entity.getDepositAmount());
        dto.setSize(entity.getSize());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        dto.setPropertyName(entity.getProperty() != null ? entity.getProperty().getPropertyName() : null);
        return dto;
    }

    // ===================== TENANT =====================
    public Tenant mapTenantRequestToEntity(TenantRequestDto dto, User user, LeaseAgreement leaseAgreement) {
        if (dto == null) return null;

        Tenant tenant = new Tenant();
        tenant.setUser(user);
        tenant.setOccupation(dto.getOccupation());
        tenant.setEmployerName(dto.getEmployerName());
        tenant.setEmployerPhone(dto.getEmployerPhone());
        tenant.setPhoto(dto.getPhoto());
        tenant.setIdCopy(dto.getIdCopy());
        tenant.setLeaseDocument(dto.getLeaseDocument());
        tenant.setEmergencyContactName(dto.getEmergencyContactName());
        tenant.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        tenant.setDateJoined(dto.getDateJoined());
        tenant.setActive(dto.isActive());
        tenant.setLeaseAgreement(leaseAgreement);
        return tenant;
    }

    public TenantResponseDto mapTenantToResponse(Tenant tenant) {
        if (tenant == null) return null;

        TenantResponseDto dto = new TenantResponseDto();
        dto.setId(tenant.getId());
        dto.setFullName(tenant.getUser() != null ? tenant.getUser().getFullName() : null);
        dto.setEmail(tenant.getUser() != null ? tenant.getUser().getEmail() : null);
        dto.setPhoneNumber(tenant.getUser() != null ? tenant.getUser().getPhoneNumber() : null);
        dto.setOccupation(tenant.getOccupation());
        dto.setEmployerName(tenant.getEmployerName());
        dto.setEmployerPhone(tenant.getEmployerPhone());
        dto.setPhoto(tenant.getPhoto());
        dto.setIdCopy(tenant.getIdCopy());
        dto.setLeaseDocument(tenant.getLeaseDocument());
        dto.setEmergencyContactName(tenant.getEmergencyContactName());
        dto.setEmergencyContactPhone(tenant.getEmergencyContactPhone());
        dto.setDateJoined(tenant.getDateJoined());
        dto.setActive(tenant.isActive());
        dto.setLeaseAgreementReference(
                tenant.getLeaseAgreement() != null ? tenant.getLeaseAgreement().getReferenceCode() : null
        );
        return dto;
    }

    // ===================== LEASE AGREEMENT =====================
    public LeaseAgreement mapLeaseRequestToEntity(LeaseAgreementRequestDto dto, Tenant tenant, Property property) {
        if (dto == null) return null;

        LeaseAgreement agreement = new LeaseAgreement();
        agreement.setReferenceCode(dto.getReferenceCode());
        agreement.setStartDate(dto.getStartDate());
        agreement.setEndDate(dto.getEndDate());
        agreement.setMonthlyRent(dto.getRentAmount());
        agreement.setTerms(dto.getTerms());
        agreement.setTenant(tenant);
        agreement.setProperty(property);
        return agreement;
    }

    public LeaseAgreementResponseDto mapLeaseToResponse(LeaseAgreement entity) {
        if (entity == null) return null;

        LeaseAgreementResponseDto dto = new LeaseAgreementResponseDto();
        dto.setId(entity.getId());
        dto.setReferenceCode(entity.getReferenceCode());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setRentAmount(entity.getMonthlyRent());
        dto.setTerms(entity.getTerms());
        dto.setTenantName(entity.getTenant() != null && entity.getTenant().getUser() != null
                ? entity.getTenant().getUser().getFullName() : null);
        dto.setPropertyName(
                entity.getProperty() != null ? entity.getProperty().getPropertyName() : null
        );

        return dto;
    }

    // ===================== INVOICE =====================
    public InvoiceResponseDto mapInvoiceToResponse(Invoice entity) {
        if (entity == null) return null;

        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(entity.getId());
        dto.setInvoiceNumber(entity.getInvoiceNumber());
        dto.setInvoiceDate(entity.getIssueDate());
        dto.setDueDate(entity.getDueDate());
        dto.setAmountDue(entity.getAmountDue());
        dto.setAmountPaid(entity.getAmountPaid());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        dto.setTenantName(entity.getTenant() != null && entity.getTenant().getUser() != null
                ? entity.getTenant().getUser().getFullName() : null);
        dto.setLeaseAgreementId(entity.getLeaseAgreement() != null ? entity.getLeaseAgreement().getId() : null);
        dto.setPropertyName(entity.getLeaseAgreement() != null && entity.getLeaseAgreement().getProperty() != null
                ? entity.getLeaseAgreement().getProperty().getPropertyName() : null);

        return dto;
    }

    public Invoice mapInvoiceRequestToEntity(InvoiceRequestDto dto, LeaseAgreement leaseAgreement, Tenant tenant) {
        if (dto == null) return null;

        Invoice entity = new Invoice();
        entity.setInvoiceNumber("INV-" + System.currentTimeMillis());
        entity.setIssueDate(dto.getInvoiceDate());
        entity.setDueDate(dto.getDueDate());
        entity.setAmountDue(dto.getAmountDue());
        entity.setAmountPaid(BigDecimal.ZERO);
        entity.setDescription(dto.getDescription());
        entity.setStatus(InvoiceStatus.PENDING);
        entity.setLeaseAgreement(leaseAgreement);
        entity.setTenant(tenant);
        return entity;
    }

    // ===================== PAYMENT =====================
    public Payment mapPaymentRequestToEntity(PaymentRequestDto dto, Invoice invoice, Tenant tenant, LeaseAgreement leaseAgreement) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setAmount(dto.getAmountPaid() != null ? BigDecimal.valueOf(dto.getAmountPaid()) : BigDecimal.ZERO);
        payment.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDateTime.now());
        payment.setInvoice(invoice);
        payment.setTenant(tenant);
        payment.setLeaseAgreement(leaseAgreement);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return payment;
    }

    public PaymentResponseDto mapPaymentToResponse(Payment entity) {
        if (entity == null) return null;

        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(entity.getId());
        dto.setPaymentReference(entity.getPaymentReference());
        dto.setPaymentMethod(entity.getPaymentMethod().name());
        dto.setPaymentStatus(entity.getPaymentStatus().name());
        dto.setAmountPaid(entity.getAmount() != null ? entity.getAmount().doubleValue() : 0.0);
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setInvoiceId(entity.getInvoice() != null ? entity.getInvoice().getId() : null);
        dto.setLeaseAgreementId(entity.getLeaseAgreement() != null ? entity.getLeaseAgreement().getId() : null);
        dto.setTenantId(entity.getTenant() != null ? entity.getTenant().getId() : null);
        dto.setTenantName(entity.getTenant() != null && entity.getTenant().getUser() != null
                ? entity.getTenant().getUser().getFullName() : null);
        dto.setPropertyName(entity.getLeaseAgreement() != null && entity.getLeaseAgreement().getProperty() != null
                ? entity.getLeaseAgreement().getProperty().getPropertyName() : null);
        return dto;
    }
}
