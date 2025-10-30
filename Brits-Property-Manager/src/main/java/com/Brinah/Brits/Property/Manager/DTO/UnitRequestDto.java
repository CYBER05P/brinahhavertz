package com.Brinah.Brits.Property.Manager.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UnitRequestDto {

    @NotBlank(message = "Unit number is required")
    private String unitNumber;

    @NotNull(message = "Rent amount is required")
    @Positive(message = "Rent amount must be positive")
    private Double rentAmount;

    @NotNull(message = "Deposit amount is required")
    @PositiveOrZero(message = "Deposit amount cannot be negative")
    private Double depositAmount;

    @NotBlank(message = "Unit size is required")
    private String size;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotBlank(message = "Status is required")
    private String status; // e.g., AVAILABLE, OCCUPIED, UNDER_MAINTENANCE

    @NotNull(message = "Property ID is required")
    private Long propertyId;
}

