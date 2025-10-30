package com.Brinah.Brits.Property.Manager.Repositories;

import com.Brinah.Brits.Property.Manager.Entities.LeaseAgreement;
import com.Brinah.Brits.Property.Manager.Enums.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseAgreementRepository extends JpaRepository<LeaseAgreement, Long> {

    List<LeaseAgreement> findByTenantId(Long tenantId);

    List<LeaseAgreement> findByPropertyId(Long propertyId);

    List<LeaseAgreement> findByStatus(LeaseStatus status);

    boolean existsByUnitIdAndStatus(Long unitId, LeaseStatus status);

    LeaseAgreement findByReferenceCode(String referenceCode);
}
