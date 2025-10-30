package com.Brinah.Brits.Property.Manager.DTO;

import com.Brinah.Brits.Property.Manager.Enums.PropertyStatus;
import com.Brinah.Brits.Property.Manager.Enums.PropertyType;
import lombok.Data;
import org.json.Property;

import java.util.List;

@Data
public class PropertyResponseDto {
    private Long id;
    private String propertyName;
    private String location;
    private PropertyType type;
    private String description;
    private String landlordName;
    private PropertyStatus status;
    private List<UnitResponseDto> units;
}
