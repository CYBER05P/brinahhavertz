package com.Brinah.Brits.Property.Manager.DTO;


import lombok.Data;

@Data
public class UnitResponseDto {
    private Long id;
    private String unitNumber;
    private Double rentAmount;
    private Double depositAmount;
    private String size;
    private String description;
    private String status;
    private String propertyName;
}
