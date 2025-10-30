
package com.Brinah.Brits.Property.Manager.DTO.Auth;

import com.Brinah.Brits.Property.Manager.Enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    // --- Authentication ---
    private String email;
    private String password;
    private Role role;   // RENTER, AGENT, LANDLORD, ADMIN, SUPERADMIN

    // --- Personal Info ---
    private String fullName;
    private String idNumber;       // National ID or Passport
    private String phoneNumber;
    private String address;
    private String gender;
    private String profilePicture; // Optional (URL or file path)

    // --- Emergency Info ---
    private String emergencyContactName;
    private String emergencyContactPhone;
}

