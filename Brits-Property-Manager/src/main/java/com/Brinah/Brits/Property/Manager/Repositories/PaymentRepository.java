package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.Invoice;
import com.Brinah.Brits.Property.Manager.Entities.Payment;
import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTenant(Tenant tenant);
    List<Payment> findByInvoice(Invoice invoice);
}
