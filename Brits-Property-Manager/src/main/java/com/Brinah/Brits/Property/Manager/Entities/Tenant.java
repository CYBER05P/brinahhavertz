package com.Brinah.Brits.Property.Manager.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tenant_profiles")
public class Tenant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Link to User Account ---
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // --- Professional Details ---
    private String occupation;

    private String employerName;

    private String employerPhone;

    // --- Documents / Media ---
    private String photo;     // Path or URL to tenant photo
    private String idCopy;    // Path or URL to scanned ID copy
    private String leaseDocument; // Optional: Path to uploaded lease agreement

    // --- Emergency Contact ---
    private String emergencyContactName;
    private String emergencyContactPhone;

    // --- Lease Info (if applicable) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id")
    private LeaseAgreement leaseAgreement;

    private LocalDate dateJoined;

    private boolean active = true;

    // --- Convenience methods ---
    public String getTenantName() {
        return user != null ? user.getFullName() : "Unknown Tenant";
    }

    public String getTenantEmail() {
        return user != null ? user.getEmail() : null;
    }

    public String getTenantPhone() {
        return user != null ? user.getPhoneNumber() : null;
    }
}
