package com.Brinah.Brits.Property.Manager.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaseAgreementRequestDto {
    private String referenceCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double rentAmount;
    private String terms;
    private Long tenantId;
    private Long propertyId;
}
