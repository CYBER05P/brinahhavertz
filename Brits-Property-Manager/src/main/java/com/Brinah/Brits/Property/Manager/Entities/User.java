package com.Brinah.Brits.Property.Manager.Entities;

import com.Brinah.Brits.Property.Manager.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User Entity — represents all system users (Admin, Landlord, Agent, Tenant, SuperAdmin).
 * Implements Spring Security's UserDetails for authentication and authorization.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    /* ==========================================================
       🔹 PRIMARY KEY
       ========================================================== */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ==========================================================
       🔹 AUTHENTICATION DETAILS
       ========================================================== */
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Stored as a BCrypt hash

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder.Default
    private boolean enabled = true;

    /* ==========================================================
       🔹 PERSONAL INFORMATION
       ========================================================== */
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String idNumber;  // National ID or Passport

    @Column(nullable = false)
    private String phoneNumber;

    private String address;
    private String gender;
    private String profilePicture; // Path or URL to image

    /* ==========================================================
       🔹 EMERGENCY CONTACT DETAILS
       ========================================================== */
    private String emergencyContactName;
    private String emergencyContactPhone;

    /* ==========================================================
       🔹 RELATIONSHIPS
       ========================================================== */

    // One landlord can own many properties
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> ownedProperties;

    // One tenant user may have a tenant profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Tenant tenantProfile;

    // Agents or Admins can create or manage many leases
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<LeaseAgreement> managedLeases;

    /* ==========================================================
       🔹 SPRING SECURITY IMPLEMENTATION
       ========================================================== */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email; // Spring Security uses email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize if you add account expiry logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize if you add lock mechanism
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize if you add password expiry logic
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /* ==========================================================
       🔹 UTILITY METHODS
       ========================================================== */
    public boolean isAdmin() {
        return this.role == Role.ADMIN || this.role == Role.SUPERADMIN;
    }

    public boolean isLandlord() {
        return this.role == Role.LANDLORD;
    }

    public boolean isTenant() {
        return this.role == Role.TENANT;
    }

    public boolean isAgent() {
        return this.role == Role.AGENT;
    }
}
