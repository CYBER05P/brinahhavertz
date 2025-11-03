package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.LeaseAgreementResponseDto;
import com.Brinah.Brits.Property.Manager.Service.Interface.LeaseAgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lease-agreements")
@RequiredArgsConstructor
public class LeaseAgreementController {

    private final LeaseAgreementService leaseAgreementService;

    // ================== CREATE LEASE AGREEMENT ==================
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'SUPERADMIN')")
    public ResponseEntity<LeaseAgreementResponseDto> createLeaseAgreement(
            @Valid @RequestBody LeaseAgreementRequestDto request) {
        LeaseAgreementResponseDto response = leaseAgreementService.createLeaseAgreement(request);
        return ResponseEntity.ok(response);
    }

    // ================== UPDATE LEASE AGREEMENT ==================
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'SUPERADMIN')")
    public ResponseEntity<LeaseAgreementResponseDto> updateLeaseAgreement(
            @PathVariable Long id,
            @Valid @RequestBody LeaseAgreementRequestDto request) {
        LeaseAgreementResponseDto response = leaseAgreementService.updateLeaseAgreement(id, request);
        return ResponseEntity.ok(response);
    }

    // ================== GET LEASE BY ID ==================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN')")
    public ResponseEntity<LeaseAgreementResponseDto> getLeaseAgreementById(@PathVariable Long id) {
        LeaseAgreementResponseDto response = leaseAgreementService.getLeaseAgreementById(id);
        return ResponseEntity.ok(response);
    }

    // ================== GET ALL LEASE AGREEMENTS ==================
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN')")
    public ResponseEntity<List<LeaseAgreementResponseDto>> getAllLeaseAgreements() {
        List<LeaseAgreementResponseDto> leases = leaseAgreementService.getAllLeaseAgreements();
        return ResponseEntity.ok(leases);
    }

    // ================== GET LEASES BY TENANT ==================
    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'LANDLORD', 'SUPERADMIN', 'RENTER')")
    public ResponseEntity<List<LeaseAgreementResponseDto>> getLeaseAgreementsByTenant(@PathVariable Long tenantId) {
        List<LeaseAgreementResponseDto> leases = leaseAgreementService.getLeaseAgreementsByTenant(tenantId);
        return ResponseEntity.ok(leases);
    }

    // ================== DELETE LEASE AGREEMENT ==================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> deleteLeaseAgreement(@PathVariable Long id) {
        leaseAgreementService.deleteLeaseAgreement(id);
        return ResponseEntity.ok("Lease agreement deleted successfully.");
    }
}
