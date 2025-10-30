package com.Brinah.Brits.Property.Manager.Entities;

import com.Brinah.Brits.Property.Manager.Enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- References ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_profile_id", nullable = false)
    private Tenant tenant; // Instead of Tenant entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id")
    private LeaseAgreement leaseAgreement;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Payment payment;

    // --- Invoice Details ---
    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private BigDecimal amountDue;

    private BigDecimal amountPaid;

    private LocalDate issueDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private String description;

    // --- Helper Methods ---
    public boolean isPaid() {
        return this.status == InvoiceStatus.PAID;
    }

    public BigDecimal getOutstandingBalance() {
        return amountDue.subtract(amountPaid == null ? BigDecimal.ZERO : amountPaid);
    }
}
