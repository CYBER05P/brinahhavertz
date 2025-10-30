package com.Brinah.Brits.Property.Manager.Service.Interface;

import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementResponseDto;

import java.util.List;

public interface LeaseAgreementService {

    LeaseAgreementResponseDto createLeaseAgreement(LeaseAgreementRequestDto request);

    LeaseAgreementResponseDto updateLeaseAgreement(Long id, LeaseAgreementRequestDto request);

    LeaseAgreementResponseDto getLeaseAgreementById(Long id);

    List<LeaseAgreementResponseDto> getAllLeaseAgreements();

    List<LeaseAgreementResponseDto> getLeaseAgreementsByTenant(Long tenantId);

    void deleteLeaseAgreement(Long id);
}
