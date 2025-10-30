package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.PropertyRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PropertyResponseDto;
import com.Brinah.Brits.Property.Manager.Service.Interface.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyResponseDto> createProperty(@RequestBody PropertyRequestDto requestDto) {
        return ResponseEntity.ok(propertyService.createProperty(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long id,
            @RequestBody PropertyRequestDto requestDto) {
        return ResponseEntity.ok(propertyService.updateProperty(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<PropertyResponseDto>> getPropertiesByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(propertyService.getPropertiesByLandlord(landlordId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponseDto>> searchPropertiesByLocation(@RequestParam String location) {
        return ResponseEntity.ok(propertyService.searchPropertiesByLocation(location));
    }
}
