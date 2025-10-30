package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.UnitRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.UnitResponseDto;
import com.Brinah.Brits.Property.Manager.Service.Interface.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    // ✅ Create new unit
    @PostMapping
    public ResponseEntity<UnitResponseDto> createUnit(@Valid @RequestBody UnitRequestDto requestDto) {
        UnitResponseDto createdUnit = unitService.createUnit(requestDto);
        return ResponseEntity.ok(createdUnit);
    }

    // ✅ Update existing unit
    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDto> updateUnit(
            @PathVariable Long id,
            @Valid @RequestBody UnitRequestDto requestDto
    ) {
        UnitResponseDto updatedUnit = unitService.updateUnit(id, requestDto);
        return ResponseEntity.ok(updatedUnit);
    }

    // ✅ Get unit by ID
    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDto> getUnitById(@PathVariable Long id) {
        UnitResponseDto unit = unitService.getUnitById(id);
        return ResponseEntity.ok(unit);
    }

    // ✅ Get all units
    @GetMapping
    public ResponseEntity<List<UnitResponseDto>> getAllUnits() {
        List<UnitResponseDto> units = unitService.getAllUnits();
        return ResponseEntity.ok(units);
    }

    // ✅ Get units by property ID
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<UnitResponseDto>> getUnitsByProperty(@PathVariable Long propertyId) {
        List<UnitResponseDto> units = unitService.getUnitsByProperty(propertyId);
        return ResponseEntity.ok(units);
    }

    // ✅ Get available units only
    @GetMapping("/available")
    public ResponseEntity<List<UnitResponseDto>> getAvailableUnits() {
        List<UnitResponseDto> availableUnits = unitService.getAvailableUnits();
        return ResponseEntity.ok(availableUnits);
    }

    // ✅ Delete unit by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.noContent().build();
    }
}
