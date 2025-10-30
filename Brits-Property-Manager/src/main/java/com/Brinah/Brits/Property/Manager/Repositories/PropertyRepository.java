package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.Property;
import com.Brinah.Brits.Property.Manager.Entities.User;
import com.Brinah.Brits.Property.Manager.Enums.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find all properties owned by a specific landlord
    List<Property> findByLandlord(User landlord);

    // Find all properties by landlord ID
    List<Property> findByLandlordId(Long landlordId);

    // Find properties by status (ACTIVE, INACTIVE, etc.)
    List<Property> findByStatus(PropertyStatus status);

    // Find property by name
    Property findByPropertyName(String propertyName);

    // Search properties by location
    List<Property> findByLocationContainingIgnoreCase(String location);
}

