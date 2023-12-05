package com.perso.bio.repository;

import com.perso.bio.model.category.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyPartRepository extends JpaRepository<BodyPart, Integer> {

}
