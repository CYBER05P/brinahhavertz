package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.DTO.UnitRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.UnitResponseDto;
import com.Brinah.Brits.Property.Manager.Entities.Property;
import com.Brinah.Brits.Property.Manager.Entities.Unit;
import com.Brinah.Brits.Property.Manager.Enums.UnitStatus;
import com.Brinah.Brits.Property.Manager.Repositories.PropertyRepository;
import com.Brinah.Brits.Property.Manager.Repositories.UnitRepository;
import com.Brinah.Brits.Property.Manager.Service.Interface.UnitService;
import com.Brinah.Brits.Property.Manager.Utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public UnitResponseDto createUnit(UnitRequestDto requestDto) {
        Property property = propertyRepository.findById(requestDto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Unit unit = new Unit();
        unit.setUnitNumber(requestDto.getUnitNumber());
        unit.setRentAmount(requestDto.getRentAmount());
        unit.setDepositAmount(requestDto.getDepositAmount());
        unit.setSize(requestDto.getSize());
        unit.setDescription(requestDto.getDescription());
        unit.setStatus(UnitStatus.valueOf(requestDto.getStatus().toUpperCase()));
        unit.setProperty(property);

        Unit saved = unitRepository.save(unit);
        return modelMapperUtil.mapUnitToResponse(saved);
    }

    @Override
    public UnitResponseDto updateUnit(Long id, UnitRequestDto requestDto) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        if (requestDto.getUnitNumber() != null) unit.setUnitNumber(requestDto.getUnitNumber());
        if (requestDto.getRentAmount() != null) unit.setRentAmount(requestDto.getRentAmount());
        if (requestDto.getDepositAmount() != null) unit.setDepositAmount(requestDto.getDepositAmount());
        if (requestDto.getSize() != null) unit.setSize(requestDto.getSize());
        if (requestDto.getDescription() != null) unit.setDescription(requestDto.getDescription());
        if (requestDto.getStatus() != null)
            unit.setStatus(UnitStatus.valueOf(requestDto.getStatus().toUpperCase()));

        Unit updated = unitRepository.save(unit);
        return modelMapperUtil.mapUnitToResponse(updated);
    }

    @Override
    public UnitResponseDto getUnitById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        return modelMapperUtil.mapUnitToResponse(unit);
    }

    @Override
    public List<UnitResponseDto> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(modelMapperUtil::mapUnitToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UnitResponseDto> getUnitsByProperty(Long propertyId) {
        return unitRepository.findByPropertyId(propertyId).stream()
                .map(modelMapperUtil::mapUnitToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UnitResponseDto> getAvailableUnits() {
        return unitRepository.findByStatus(UnitStatus.AVAILABLE).stream()
                .map(modelMapperUtil::mapUnitToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUnit(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        unitRepository.delete(unit);
    }
}
