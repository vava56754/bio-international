package com.perso.bio.repository;

import com.perso.bio.enums.ProcurementState;
import com.perso.bio.model.procurement.Procurement;
import com.perso.bio.model.user_management.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Integer> {

    Optional<List<Procurement>> findAllProcurementByUser(User user);

    Optional<Procurement> findProcurementByUserAndProcurementState(User user, ProcurementState procurementState);

    Optional<List<Procurement>> findAllProcurementByUserAndProcurementState(User user, ProcurementState procurementState);
}
