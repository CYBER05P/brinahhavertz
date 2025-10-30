package com.Brinah.Brits.Property.Manager.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LeaseAgreementResponseDto {
    private Long id;
    private String referenceCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double rentAmount;
    private String terms;
    private String tenantName;
    private String propertyName;
}
