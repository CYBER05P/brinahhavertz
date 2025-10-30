package com.Brinah.Brits.Property.Manager.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "log_entries")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User performing the action
    @Column(nullable = false)
    private String username;

    // Role of the user (e.g., ADMIN, SUPERADMIN, TENANT)
    @Column(nullable = false)
    private String role;

    // Client IP address
    private String ipAddress;

    // High-level action (LOGIN, CREATE, UPDATE, DELETE, ACCESS_CONTROLLER, etc.)
    @Column(nullable = false)
    private String actionType;

    // The specific endpoint or resource accessed
    private String endpoint;

    // Optional description / extra info (JSON, error message, etc.)
    @Column(length = 2000)
    private String details;

    // Success / Failure flag for actions like login
    private boolean success;

    // Timestamp when the log was recorded
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // For easy filtering by module (e.g., FLIGHT, BOOKING, PAYMENT, TENANT)
    private String module;
}
