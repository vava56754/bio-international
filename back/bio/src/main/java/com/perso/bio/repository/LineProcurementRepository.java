package com.perso.bio.repository;

import com.perso.bio.model.procurement.LineProcurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineProcurementRepository extends JpaRepository<LineProcurement, Integer> {

    Optional<List<LineProcurement>> findAllByProcurementProcurementId(Integer procurementId);

    Optional<LineProcurement> findByProductProductIdAndProcurementProcurementId(Integer productId, Integer procurementId);
}
