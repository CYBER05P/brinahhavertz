package com.Brinah.Brits.Property.Manager.Controllers;

import com.Brinah.Brits.Property.Manager.DTO.Auth.LoginRequest;
import com.Brinah.Brits.Property.Manager.DTO.Auth.LoginResponse;
import com.Brinah.Brits.Property.Manager.DTO.Auth.RegisterRequest;
import com.Brinah.Brits.Property.Manager.DTO.Auth.RegisterResponse;
import com.Brinah.Brits.Property.Manager.Entities.User;
import com.Brinah.Brits.Property.Manager.Repositories.UserRepository;
import com.Brinah.Brits.Property.Manager.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // -------------------------
    // REGISTER
    // -------------------------
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(RegisterResponse.builder()
                            .message("❌ Email is already in use")
                            .build());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .fullName(request.getFullName())
                .idNumber(request.getIdNumber())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .gender(request.getGender())
                .profilePicture(request.getProfilePicture())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactPhone(request.getEmergencyContactPhone())
                .enabled(true)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(RegisterResponse.builder()
                .message("✅ User registered successfully")
                .username(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build());
    }

    // -------------------------
    // LOGIN
    // -------------------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Authenticate with Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Fetch user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("❌ User not found"));

        // Generate JWT including role claim
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(LoginResponse.builder()
                .token(token)
                .username(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build());
    }
}
