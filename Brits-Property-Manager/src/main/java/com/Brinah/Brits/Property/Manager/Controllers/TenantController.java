package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import com.Brinah.Brits.Property.Manager.Service.Interface.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    // ================== CREATE TENANT ==================
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'SUPERADMIN')")
    public ResponseEntity<Tenant> createTenant(@Valid @RequestBody Tenant tenant) {
        Tenant savedTenant = tenantService.createTenant(tenant);
        return ResponseEntity.ok(savedTenant);
    }

    // ================== UPDATE TENANT ==================
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'SUPERADMIN')")
    public ResponseEntity<Tenant> updateTenant(
            @PathVariable Long id,
            @Valid @RequestBody Tenant updatedTenant) {
        Tenant tenant = tenantService.updateTenant(id, updatedTenant);
        return ResponseEntity.ok(tenant);
    }

    // ================== GET TENANT BY ID ==================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN', 'RENTER')")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================== GET TENANT BY USER EMAIL ==================
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN', 'RENTER')")
    public ResponseEntity<Tenant> getTenantByUserEmail(@RequestParam String email) {
        return tenantService.getTenantByUserEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================== GET ALL TENANTS ==================
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN')")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    // ================== GET ACTIVE TENANTS ==================
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN')")
    public ResponseEntity<List<Tenant>> getActiveTenants() {
        List<Tenant> activeTenants = tenantService.getActiveTenants();
        return ResponseEntity.ok(activeTenants);
    }

    // ================== DEACTIVATE TENANT ==================
    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> deactivateTenant(@PathVariable Long id) {
        tenantService.deactivateTenant(id);
        return ResponseEntity.ok("Tenant deactivated successfully.");
    }

    // ================== ACTIVATE TENANT ==================
    @PutMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> activateTenant(@PathVariable Long id) {
        tenantService.activateTenant(id);
        return ResponseEntity.ok("Tenant activated successfully.");
    }

    // ================== DELETE TENANT ==================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.ok("Tenant deleted successfully.");
    }
}
