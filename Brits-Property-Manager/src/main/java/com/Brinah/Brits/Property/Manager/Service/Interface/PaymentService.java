package com.Brinah.Brits.Property.Manager.Service.Interface;

import com.Brinah.Brits.Property.Manager.DTO.PaymentRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PaymentResponseDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    // Create or record a new payment
    PaymentResponseDto recordPayment(PaymentRequestDto paymentRequest);

    // Update an existing payment
    PaymentResponseDto updatePayment(Long id, PaymentRequestDto paymentRequest);

    // Retrieve a specific payment by ID
    Optional<PaymentResponseDto> getPaymentById(Long id);

    // Retrieve all payments
    List<PaymentResponseDto> getAllPayments();

    // Retrieve payments belonging to a specific tenant
    List<PaymentResponseDto> getPaymentsByTenant(Long tenantId);

    // Retrieve payments linked to a specific invoice
    List<PaymentResponseDto> getPaymentsByInvoice(Long invoiceId);

    // Mark a payment as successful
    PaymentResponseDto markPaymentSuccessful(Long id, String transactionId);

    // Mark a payment as failed
    PaymentResponseDto markPaymentFailed(Long id, String reason);

    // Delete a payment record
    void deletePayment(Long id);
}
