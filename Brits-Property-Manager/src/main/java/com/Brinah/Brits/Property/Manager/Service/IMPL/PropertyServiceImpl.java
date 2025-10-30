package com.Brinah.Brits.Property.Manager.Service.IMPL;

import com.Brinah.Brits.Property.Manager.DTO.PropertyRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PropertyResponseDto;
import com.Brinah.Brits.Property.Manager.Entities.Property;
import com.Brinah.Brits.Property.Manager.Entities.User;
import com.Brinah.Brits.Property.Manager.Enums.PropertyStatus;
import com.Brinah.Brits.Property.Manager.Repositories.PropertyRepository;
import com.Brinah.Brits.Property.Manager.Repositories.UserRepository;
import com.Brinah.Brits.Property.Manager.Service.Interface.PropertyService;
import com.Brinah.Brits.Property.Manager.Utils.ModelMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public PropertyResponseDto createProperty(PropertyRequestDto requestDto) {
        User landlord = userRepository.findById(requestDto.getLandlordId())
                .orElseThrow(() -> new EntityNotFoundException("Landlord not found"));

        Property property = Property.builder()
                .propertyName(requestDto.getPropertyName())
                .location(requestDto.getLocation())
                .type(requestDto.getType()) // ✅ fixed
                .description(requestDto.getDescription())
                .status(PropertyStatus.ACTIVE)
                .landlord(landlord)
                .build();

        Property saved = propertyRepository.save(property);
        return modelMapperUtil.mapPropertyToResponse(saved);
    }

    @Override
    public PropertyResponseDto updateProperty(Long id, PropertyRequestDto requestDto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        property.setPropertyName(requestDto.getPropertyName());
        property.setLocation(requestDto.getLocation());
        property.setType(requestDto.getType()); // ✅ fixed
        property.setDescription(requestDto.getDescription());

        if (requestDto.getLandlordId() != null) {
            User landlord = userRepository.findById(requestDto.getLandlordId())
                    .orElseThrow(() -> new EntityNotFoundException("Landlord not found"));
            property.setLandlord(landlord);
        }

        Property updated = propertyRepository.save(property);
        return modelMapperUtil.mapPropertyToResponse(updated);
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
        propertyRepository.delete(property);
    }

    @Override
    public PropertyResponseDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
        return modelMapperUtil.mapPropertyToResponse(property);
    }

    @Override
    public List<PropertyResponseDto> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(modelMapperUtil::mapPropertyToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponseDto> getPropertiesByLandlord(Long landlordId) {
        List<Property> properties = propertyRepository.findByLandlordId(landlordId);
        return properties.stream()
                .map(modelMapperUtil::mapPropertyToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponseDto> searchPropertiesByLocation(String location) {
        List<Property> properties = propertyRepository.findByLocationContainingIgnoreCase(location);
        return properties.stream()
                .map(modelMapperUtil::mapPropertyToResponse)
                .collect(Collectors.toList());
    }
}
