package com.Brinah.Brits.Property.Manager.Service.Interface;


import com.Brinah.Brits.Property.Manager.DTO.PropertyRequestDto;
import com.Brinah.Brits.Property.Manager.DTO.PropertyResponseDto;

import java.util.List;

public interface PropertyService {

    PropertyResponseDto createProperty(PropertyRequestDto requestDto);

    PropertyResponseDto updateProperty(Long id, PropertyRequestDto requestDto);

    void deleteProperty(Long id);

    PropertyResponseDto getPropertyById(Long id);

    List<PropertyResponseDto> getAllProperties();

    List<PropertyResponseDto> getPropertiesByLandlord(Long landlordId);

    List<PropertyResponseDto> searchPropertiesByLocation(String location);
}
