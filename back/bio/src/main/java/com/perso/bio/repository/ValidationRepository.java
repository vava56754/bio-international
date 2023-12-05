package com.perso.bio.repository;

import com.perso.bio.model.user_management.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);

}
