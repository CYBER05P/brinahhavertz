package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.Unit;
import com.Brinah.Brits.Property.Manager.Enums.UnitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    // Find all units for a specific property
    List<Unit> findByPropertyId(Long propertyId);

    // Find by status (AVAILABLE, OCCUPIED, etc.)
    List<Unit> findByStatus(UnitStatus status);

    // Find by unit number (unique within property)
    Unit findByUnitNumber(String unitNumber);
}
