package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import com.Brinah.Brits.Property.Manager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    // Find by associated user account
    Optional<Tenant> findByTenant(Tenant tenant);

    // Find by email through the User relationship
    Optional<Tenant> findByUserEmail(String email);

    // Search tenants by status, name, or ID
    List<Tenant> findByActiveTrue();

    List<Tenant> findByUserFullNameContainingIgnoreCase(String name);
}
