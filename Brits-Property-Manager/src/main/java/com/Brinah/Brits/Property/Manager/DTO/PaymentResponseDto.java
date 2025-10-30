package com.Brinah.Brits.Property.Manager.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {

    private Long id;
    private String paymentReference;
    private String paymentMethod;
    private String paymentStatus;
    private Double amountPaid;
    private LocalDateTime paymentDate;
    private Long invoiceId;
    private String tenantName;
    private String propertyName;
    private Long tenantId;
    private Long leaseAgreementId;
}
