package com.Brinah.Brits.Property.Manager.DTO.Auth;


import com.Brinah.Brits.Property.Manager.Enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;       // JWT token
    private String username;
    private String email;
    private Role role;
}
