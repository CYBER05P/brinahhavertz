package com.Brinah.Brits.Property.Manager.Entities;

import com.Brinah.Brits.Property.Manager.Enums.LeaseStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lease_agreements")
public class LeaseAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String referenceCode;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double monthlyRent;
    private Double deposit;
    private String terms;
    private String leaseDocument;

    @Enumerated(EnumType.STRING)
    private LeaseStatus status;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

}
