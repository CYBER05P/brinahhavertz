package com.Brinah.Brits.Property.Manager.DTO;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean active;
}
