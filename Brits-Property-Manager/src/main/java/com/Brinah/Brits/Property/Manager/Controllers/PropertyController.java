package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.PropertyRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PropertyResponseDto;
import com.Brinah.Brits.Property.Manager.Service.Interface.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // ================================
    // CREATE PROPERTY — Landlord/Admin Only
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> createProperty(@RequestBody PropertyRequestDto requestDto) {
        return ResponseEntity.ok(propertyService.createProperty(requestDto));
    }

    // ================================
    // UPDATE PROPERTY — Landlord/Admin Only
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long id,
            @RequestBody PropertyRequestDto requestDto) {
        return ResponseEntity.ok(propertyService.updateProperty(id, requestDto));
    }

    // ================================
    // DELETE PROPERTY — Admin or Superadmin Only
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // ================================
    // GET PROPERTY BY ID — All Roles
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    // ================================
    // GET ALL PROPERTIES — All Roles
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    // ================================
    // GET PROPERTIES BY LANDLORD — Admin & Landlord
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT')")
    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<PropertyResponseDto>> getPropertiesByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(propertyService.getPropertiesByLandlord(landlordId));
    }

    // ================================
    // SEARCH BY LOCATION — Open to All Roles
    // ================================
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'LANDLORD', 'AGENT', 'TENANT', 'RENTER')")
    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponseDto>> searchPropertiesByLocation(@RequestParam String location) {
        return ResponseEntity.ok(propertyService.searchPropertiesByLocation(location));
    }
}
