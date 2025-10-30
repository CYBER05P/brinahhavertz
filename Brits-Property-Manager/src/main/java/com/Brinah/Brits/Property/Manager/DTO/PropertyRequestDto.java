package com.Brinah.Brits.Property.Manager.DTO;

import com.Brinah.Brits.Property.Manager.Enums.PropertyType;
import lombok.Data;

@Data
public class PropertyRequestDto {
    private String propertyName;
    private String location;
    private PropertyType type; // Apartment, Office, Shop, etc.
    private String description;
    private Long landlordId;
}
