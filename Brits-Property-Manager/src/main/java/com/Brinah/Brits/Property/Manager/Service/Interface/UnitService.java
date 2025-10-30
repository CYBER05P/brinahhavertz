package com.Brinah.Brits.Property.Manager.Service.Interface;

import com.Brinah.Brits.Property.Manager.DTO.UnitRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.UnitResponseDto;

import java.util.List;

public interface UnitService {
    UnitResponseDto createUnit(UnitRequestDto requestDto);
    UnitResponseDto updateUnit(Long id, UnitRequestDto requestDto);
    UnitResponseDto getUnitById(Long id);
    List<UnitResponseDto> getAllUnits();
    List<UnitResponseDto> getUnitsByProperty(Long propertyId);
    void deleteUnit(Long id);
    List<UnitResponseDto> getAvailableUnits();
}
