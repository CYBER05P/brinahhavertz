package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.Entities.Invoice;
import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import com.Brinah.Brits.Property.Manager.Enums.InvoiceStatus;
import com.Brinah.Brits.Property.Manager.Repositories.InvoiceRepository;
import com.Brinah.Brits.Property.Manager.Repositories.TenantRepository;
import com.Brinah.Brits.Property.Manager.Service.Interface.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TenantRepository tenantRepository;

    /* ==========================================================
       🔹 CREATE INVOICE
       ========================================================== */
    @Override
    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            invoice.setInvoiceNumber(generateInvoiceNumber());
        }

        if (invoice.getStatus() == null) {
            invoice.setStatus(InvoiceStatus.PENDING);
        }

        if (invoice.getIssueDate() == null) {
            invoice.setIssueDate(LocalDate.now());
        }

        if (invoice.getDueDate() == null) {
            invoice.setDueDate(LocalDate.now().plusDays(30));
        }

        if (invoice.getAmountPaid() == null) {
            invoice.setAmountPaid(BigDecimal.ZERO);
        }

        return invoiceRepository.save(invoice);
    }

    /* ==========================================================
       🔹 UPDATE INVOICE
       ========================================================== */
    @Override
    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        Invoice existing = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));

        if (updatedInvoice.getAmountDue() != null)
            existing.setAmountDue(updatedInvoice.getAmountDue());

        if (updatedInvoice.getAmountPaid() != null)
            existing.setAmountPaid(updatedInvoice.getAmountPaid());

        if (updatedInvoice.getDueDate() != null)
            existing.setDueDate(updatedInvoice.getDueDate());

        if (updatedInvoice.getDescription() != null)
            existing.setDescription(updatedInvoice.getDescription());

        if (updatedInvoice.getStatus() != null)
            existing.setStatus(updatedInvoice.getStatus());

        return invoiceRepository.save(existing);
    }

    /* ==========================================================
       🔹 GET INVOICE BY ID
       ========================================================== */
    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    /* ==========================================================
       🔹 GET ALL INVOICES
       ========================================================== */
    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    /* ==========================================================
       🔹 GET INVOICES BY TENANT
       ========================================================== */
    @Override
    public List<Invoice> getInvoicesByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Tenant not found with id " + tenantId));

        return invoiceRepository.findByTenant(tenant);
    }

    @Override
    public List<Invoice> getInvoicesByTenantProfile(Long tenantProfileId) {
        return List.of();
    }

    /* ==========================================================
       🔹 DELETE INVOICE
       ========================================================== */
    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found with id " + id);
        }
        invoiceRepository.deleteById(id);
    }

    /* ==========================================================
       🔹 MARK AS PAID
       ========================================================== */
    @Override
    public Invoice markAsPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));

        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setAmountPaid(invoice.getAmountDue());
        return invoiceRepository.save(invoice);
    }

    /* ==========================================================
       🔹 MARK AS OVERDUE
       ========================================================== */
    @Override
    public Invoice markAsOverdue(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));

        if (invoice.getStatus() != InvoiceStatus.PAID &&
                LocalDate.now().isAfter(invoice.getDueDate())) {
            invoice.setStatus(InvoiceStatus.OVERDUE);
        }

        return invoiceRepository.save(invoice);
    }

    /* ==========================================================
       🔹 HELPER: GENERATE UNIQUE INVOICE NUMBER
       ========================================================== */
    private String generateInvoiceNumber() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "INV-" + LocalDate.now().getYear() + "-" + uniqueId;
    }
}
