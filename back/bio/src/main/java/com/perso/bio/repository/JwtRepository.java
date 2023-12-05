package com.perso.bio.repository;

import com.perso.bio.model.user_management.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Integer> {

    Optional<Jwt> findByJwtValueAndJwtDisableAndJwtExpire(String value, boolean disable, boolean expire);

    @Query("FROM Jwt j WHERE j.jwtExpire = :expire AND j.jwtDisable = :disable AND j.user.userMail = :mail")
    Optional<Jwt> findUserValidToken(@Param("mail") String mail, @Param("disable") boolean disable, @Param("expire") boolean expire);

    @Query("FROM Jwt j WHERE j.user.userMail = :mail")
    Stream<Jwt> findUserByMail(@Param("mail") String mail);

    void deleteAllByJwtExpireAndJwtDisable(boolean expire, boolean disable);
}
