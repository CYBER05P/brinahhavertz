package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.PaymentRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PaymentResponseDto;
import com.Brinah.Brits.Property.Manager.Service.Interface.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ✅ Allow frontend access
public class PaymentController {

    private final PaymentService paymentService;

    /* ==========================================================
       🔹 CREATE PAYMENT — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PostMapping
    public ResponseEntity<PaymentResponseDto> recordPayment(@RequestBody PaymentRequestDto request) {
        PaymentResponseDto response = paymentService.recordPayment(request);
        return ResponseEntity.ok(response);
    }

    /* ==========================================================
       🔹 UPDATE PAYMENT — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequestDto request) {
        PaymentResponseDto response = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(response);
    }

    /* ==========================================================
       🔹 GET PAYMENT BY ID — All roles
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ==========================================================
       🔹 GET ALL PAYMENTS — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        List<PaymentResponseDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    /* ==========================================================
       🔹 GET PAYMENTS BY TENANT — All roles
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByTenant(@PathVariable Long tenantId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsByTenant(tenantId);
        return ResponseEntity.ok(payments);
    }

    /* ==========================================================
       🔹 GET PAYMENTS BY INVOICE — All roles
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByInvoice(@PathVariable Long invoiceId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsByInvoice(invoiceId);
        return ResponseEntity.ok(payments);
    }

    /* ==========================================================
       🔹 MARK PAYMENT SUCCESSFUL — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PatchMapping("/{id}/success")
    public ResponseEntity<PaymentResponseDto> markPaymentSuccessful(
            @PathVariable Long id,
            @RequestParam String transactionId) {
        PaymentResponseDto response = paymentService.markPaymentSuccessful(id, transactionId);
        return ResponseEntity.ok(response);
    }

    /* ==========================================================
       🔹 MARK PAYMENT FAILED — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PatchMapping("/{id}/fail")
    public ResponseEntity<PaymentResponseDto> markPaymentFailed(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        PaymentResponseDto response = paymentService.markPaymentFailed(id, reason);
        return ResponseEntity.ok(response);
    }

    /* ==========================================================
       🔹 DELETE PAYMENT — Admins, Landlords, Agents
       ========================================================== */
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
