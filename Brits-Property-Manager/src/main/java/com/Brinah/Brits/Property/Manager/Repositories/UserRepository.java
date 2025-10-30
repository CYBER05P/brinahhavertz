package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if ID number already exists (useful for validation)
    boolean existsByIdNumber(String idNumber);

    // Check if phone number already exists (optional but good for uniqueness)
    boolean existsByPhoneNumber(String phoneNumber);
}
