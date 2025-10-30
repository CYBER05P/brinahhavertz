package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.Entities.Tenant;
import com.Brinah.Brits.Property.Manager.Entities.User;
import com.Brinah.Brits.Property.Manager.Repositories.TenantRepository;
import com.Brinah.Brits.Property.Manager.Repositories.UserRepository;
import com.Brinah.Brits.Property.Manager.Service.Interface.TenantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository TenantRepository;
    private final UserRepository userRepository;

    @Override
    public Tenant createTenant(Tenant Tenant) {
        // Ensure linked user exists
        if (Tenant.getUser() != null && Tenant.getUser().getId() != null) {
            User user = userRepository.findById(Tenant.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            Tenant.setUser(user);
        }
        Tenant.setActive(true);
        return TenantRepository.save(Tenant);
    }

    @Override
    public Tenant updateTenant(Long id, Tenant updatedProfile) {
        Tenant existing = TenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant profile not found"));

        existing.setOccupation(updatedProfile.getOccupation());
        existing.setEmployerName(updatedProfile.getEmployerName());
        existing.setEmergencyContactPhone(updatedProfile.getEmergencyContactPhone());
        existing.setPhoto(updatedProfile.getPhoto());
        existing.setIdCopy(updatedProfile.getIdCopy());
        existing.setLeaseAgreement(updatedProfile.getLeaseAgreement());
        existing.setActive(updatedProfile.isActive());

        return TenantRepository.save(existing);
    }

    @Override
    public Optional<Tenant> getTenantById(Long id) {
        return TenantRepository.findById(id);
    }

    @Override
    public Optional<Tenant> getTenantByUserEmail(String email) {
        return TenantRepository.findByUserEmail(email);
    }

    @Override
    public List<Tenant> getAllTenants() {
        return TenantRepository.findAll();
    }

    @Override
    public List<Tenant> getActiveTenants() {
        return TenantRepository.findByActiveTrue();
    }

    @Override
    public void deactivateTenant(Long id) {
        Tenant Tenant = TenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant profile not found"));
        Tenant.setActive(false);
        TenantRepository.save(Tenant);
    }

    @Override
    public void activateTenant(Long id) {
        Tenant Tenant = TenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant profile not found"));
        Tenant.setActive(true);
        TenantRepository.save(Tenant);
    }

    @Override
    public void deleteTenant(Long id) {
        TenantRepository.deleteById(id);
    }
}
