package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.Invoice;
import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByTenantProfile(Tenant tenant);
    List<Invoice> findByTenant(Tenant tenant);

    // (Optional) Find invoices by status
    List<Invoice> findByStatus(String status);

}
