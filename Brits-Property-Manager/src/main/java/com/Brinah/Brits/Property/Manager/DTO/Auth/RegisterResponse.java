package com.Brinah.Brits.Property.Manager.DTO.Auth;


import com.Brinah.Brits.Property.Manager.Enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {
    private String message;     // e.g. "User registered successfully"
    private String username;
    private String email;
    private Role role;
}
