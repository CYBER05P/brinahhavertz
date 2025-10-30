package com.Brinah.Brits.Property.Manager.Service.Interface;


import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import java.util.List;
import java.util.Optional;

public interface TenantService {

    Tenant createTenant(Tenant Tenant);

    Tenant updateTenant(Long id, Tenant updatedProfile);

    Optional<Tenant> getTenantById(Long id);

    Optional<Tenant> getTenantByUserEmail(String email);

    List<Tenant> getAllTenants();

    List<Tenant> getActiveTenants();

    void deactivateTenant(Long id);

    void activateTenant(Long id);

    void deleteTenant(Long id);
}
