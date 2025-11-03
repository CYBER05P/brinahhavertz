package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.Entities.Invoice;
import com.Brinah.Brits.Property.Manager.Service.Interface.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ✅ Allow frontend integration (React/Vaadin)
public class InvoiceController {

    private final InvoiceService invoiceService;

    /* ==========================================================
       🔹 CREATE INVOICE — Admins / Landlords Only
       ========================================================== */
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice created = invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(created);
    }

    /* ==========================================================
       🔹 UPDATE INVOICE — Admins / Landlords Only
       ========================================================== */
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(
            @PathVariable Long id,
            @RequestBody Invoice updatedInvoice) {
        Invoice updated = invoiceService.updateInvoice(id, updatedInvoice);
        return ResponseEntity.ok(updated);
    }

    /* ==========================================================
       🔹 GET INVOICE BY ID — Tenant can only access their own
       ========================================================== */
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ==========================================================
       🔹 GET ALL INVOICES — Admins / Landlords Only
       ========================================================== */
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    /* ==========================================================
       🔹 GET INVOICES BY TENANT — Tenant can view their own
       ========================================================== */
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Invoice>> getInvoicesByTenant(@PathVariable Long tenantId) {
        List<Invoice> invoices = invoiceService.getInvoicesByTenant(tenantId);
        return ResponseEntity.ok(invoices);
    }

    /* ==========================================================
       🔹 MARK INVOICE AS PAID — Admins / Landlords Only
       ========================================================== */
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @PatchMapping("/{id}/paid")
    public ResponseEntity<Invoice> markAsPaid(@PathVariable Long id) {
        Invoice invoice = invoiceService.markAsPaid(id);
        return ResponseEntity.ok(invoice);
    }

    /* ==========================================================
       🔹 MARK INVOICE AS OVERDUE — Admins / Landlords Only
       ========================================================== */
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD', 'SUPERADMIN')")
    @PatchMapping("/{id}/overdue")
    public ResponseEntity<Invoice> markAsOverdue(@PathVariable Long id) {
        Invoice invoice = invoiceService.markAsOverdue(id);
        return ResponseEntity.ok(invoice);
    }

    /* ==========================================================
       🔹 DELETE INVOICE — Superadmin Only
       ========================================================== */
    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
