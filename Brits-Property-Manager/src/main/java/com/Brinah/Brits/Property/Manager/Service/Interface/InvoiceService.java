package com.Brinah.Brits.Property.Manager.Service.Interface;

import com.Brinah.Brits.Property.Manager.Entities.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    Invoice createInvoice(Invoice invoice);

    Invoice updateInvoice(Long id, Invoice invoice);

    Optional<Invoice> getInvoiceById(Long id);

    List<Invoice> getAllInvoices();

    List<Invoice> getInvoicesByTenant(Long tenantId);

    List<Invoice> getInvoicesByTenantProfile(Long tenantProfileId);

    void deleteInvoice(Long id);

    Invoice markAsPaid(Long id);

    Invoice markAsOverdue(Long id);
}
