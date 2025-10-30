package com.Brinah.Brits.Property.Manager.DTO;


import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDto {

    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal amountDue;
    private BigDecimal amountPaid;
    private String description;
    private String status;

    private Long leaseAgreementId;
    private String tenantName;
    private String propertyName;
}


