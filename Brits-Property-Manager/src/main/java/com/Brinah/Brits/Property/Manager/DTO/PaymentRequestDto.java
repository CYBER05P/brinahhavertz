package com.Brinah.Brits.Property.Manager.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDto {
    private Double amountPaid;
    private String paymentMethod; // M-PESA, BANK, CASH
    private String transactionId; // M-PESA or BANK transaction ID
    private LocalDateTime paymentDate;
    private Long invoiceId;
}
