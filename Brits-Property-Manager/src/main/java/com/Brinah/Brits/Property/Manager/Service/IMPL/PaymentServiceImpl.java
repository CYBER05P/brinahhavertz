package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.DTO.PaymentRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PaymentResponseDto;
import com.Brinah.Brits.Property.Manager.Entities.*;
import com.Brinah.Brits.Property.Manager.Enums.InvoiceStatus;
import com.Brinah.Brits.Property.Manager.Enums.PaymentMethod;
import com.Brinah.Brits.Property.Manager.Enums.PaymentStatus;
import com.Brinah.Brits.Property.Manager.Repositories.*;
import com.Brinah.Brits.Property.Manager.Service.Interface.PaymentService;
import com.Brinah.Brits.Property.Manager.Utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;
    private final InvoiceRepository invoiceRepository;
    private final LeaseAgreementRepository leaseAgreementRepository;
    private final ModelMapperUtil modelMapperUtil;

    /* ==========================================================
       🔹 CREATE PAYMENT
       ========================================================== */
    @Override
    public PaymentResponseDto recordPayment(PaymentRequestDto request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Tenant tenant = invoice.getTenant();
        LeaseAgreement lease = invoice.getLeaseAgreement();

        Payment payment = Payment.builder()
                .tenant(tenant)
                .leaseAgreement(lease)
                .invoice(invoice)
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()))
                .paymentStatus(PaymentStatus.SUCCESS)
                .amount(BigDecimal.valueOf(request.getAmountPaid()))
                .TransactionId(request.getTransactionId()) // ✅ lowercase field
                .paymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDateTime.now())
                .remarks("Payment recorded successfully")
                .build();

        Payment saved = paymentRepository.save(payment);

        // ✅ Update invoice status and paid amount
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setAmountPaid(payment.getAmount());
        invoiceRepository.save(invoice);

        return mapToResponse(saved);
    }

    /* ==========================================================
       🔹 UPDATE PAYMENT
       ========================================================== */
    @Override
    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto request) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existing.setAmount(BigDecimal.valueOf(request.getAmountPaid()));
        existing.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase()));
        existing.setTransactionId(request.getTransactionId());
        existing.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDateTime.now());

        Payment updated = paymentRepository.save(existing);
        return mapToResponse(updated);
    }

    /* ==========================================================
       🔹 GET PAYMENT BY ID
       ========================================================== */
    @Override
    public Optional<PaymentResponseDto> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(this::mapToResponse);
    }

    /* ==========================================================
       🔹 GET ALL PAYMENTS
       ========================================================== */
    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ==========================================================
       🔹 GET PAYMENTS BY TENANT
       ========================================================== */
    @Override
    public List<PaymentResponseDto> getPaymentsByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        return paymentRepository.findByTenant(tenant)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ==========================================================
       🔹 GET PAYMENTS BY INVOICE
       ========================================================== */
    @Override
    public List<PaymentResponseDto> getPaymentsByInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return paymentRepository.findByInvoice(invoice)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ==========================================================
       🔹 MARK PAYMENT SUCCESSFUL
       ========================================================== */
    @Override
    public PaymentResponseDto markPaymentSuccessful(Long id, String transactionId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(transactionId);
        payment.setRemarks("Payment marked as successful");
        return mapToResponse(paymentRepository.save(payment));
    }

    /* ==========================================================
       🔹 MARK PAYMENT FAILED
       ========================================================== */
    @Override
    public PaymentResponseDto markPaymentFailed(Long id, String reason) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentStatus(PaymentStatus.FAILED);
        payment.setRemarks(reason != null ? reason : "Payment failed");
        return mapToResponse(paymentRepository.save(payment));
    }

    /* ==========================================================
       🔹 DELETE PAYMENT
       ========================================================== */
    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentRepository.delete(payment);
    }

    /* ==========================================================
       🔹 HELPER METHOD: MAP ENTITY TO DTO
       ========================================================== */
    private PaymentResponseDto mapToResponse(Payment payment) {
        PaymentResponseDto dto = modelMapperUtil.mapToDto(payment, PaymentResponseDto.class);

        if (payment.getTenant() != null) {
            dto.setTenantId(payment.getTenant().getId());
            dto.setTenantName(payment.getTenant().getTenantName());
        }

        if (payment.getLeaseAgreement() != null) {
            dto.setLeaseAgreementId(payment.getLeaseAgreement().getId());
            if (payment.getLeaseAgreement().getUnit() != null &&
                    payment.getLeaseAgreement().getUnit().getProperty() != null) {
                dto.setPropertyName(payment.getLeaseAgreement().getUnit().getProperty().getPropertyName());
            }
        }

        if (payment.getInvoice() != null) {
            dto.setInvoiceId(payment.getInvoice().getId());
        }

        dto.setAmountPaid(payment.getAmount() != null ? payment.getAmount().doubleValue() : 0.0);
        dto.setPaymentStatus(payment.getPaymentStatus() != null ? payment.getPaymentStatus().name() : PaymentStatus.PENDING.name());

        return dto;
    }
}
