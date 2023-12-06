package com.perso.bio.service.jwt;

import com.perso.bio.config.KeyPairConfig;
import com.perso.bio.constants.Field;
import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.user_management.Jwt;
import com.perso.bio.model.user_management.User;
import com.perso.bio.repository.JwtRepository;
import com.perso.bio.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.*;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;

import java.util.function.Function;

@Service
@Qualifier("jpa")
public class JwtService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtService.class);

    private final UserDetailsService userDetailsService;

    private final JwtRepository jwtRepository;

    private final UserRepository userRepository;

    private final KeyPair keyPair;

    public JwtService(UserDetailsService userDetailsService, JwtRepository jwtRepository, UserRepository userRepository) throws NoSuchAlgorithmException {
        this.keyPair = KeyPairConfig.getKeyPair();
        this.userDetailsService = userDetailsService;
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
    }

    public Jwt getTokenByValue(String value) {
        return this.jwtRepository.findByJwtValueAndJwtDisableAndJwtExpire(value, false, false)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.TOKEN_UNKNOWN));
    }


    public Map<String, String> generate(String username) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        log.info("[JwtSerivce::generate] info : " + userDetails.toString());
        User user = this.userRepository.findByUserMail(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException(MessageConstants.JWT_SERVICE_USER_ERROR_MESSAGE + userDetails.getUsername()));
        Map<String, String> jwtMap = this.generateJwt(userDetails);
        final Jwt jwt = new Jwt
                .Builder(jwtMap.get(Field.BEARER), user)
                .disable(false)
                .expire(false)
                .build();
        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    public Map<String, String> generateJwt(UserDetails userDetails) {

        final long currentTime = System.currentTimeMillis();

        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(Field.NOM, userDetails.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, userDetails.getUsername(),
                "role", ((User) userDetails).getRole().getRoleName());

        final String token = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(userDetails.getUsername())
                .claims(claims)
                .signWith(this.keyPair.getPrivate())
                .compact();
        return Map.of(Field.BEARER, token);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpirationDateFromToken(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);

    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        log.info(claims.toString());
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {

        var claims = Jwts.parser()
                .verifyWith(getRsaPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.info(MessageConstants.ALL_CLAIMS + claims.toString());
        return claims;
    }

    private PublicKey getRsaPublicKey() {
        return this.keyPair.getPublic();
    }

    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUserValidToken(user.getUserMail(), false, false)
                .orElseThrow(() -> new NoSuchElementException(MessageConstants.TOKEN_INVALID));

        jwt.setJwtExpire(true);
        jwt.setJwtDisable(true);
        this.jwtRepository.save(jwt);
    }

    @Scheduled(cron = "@daily")
    public void removeUselessJwt() {
        log.info(MessageConstants.DELETE_TOKEN, Instant.now());
        this.jwtRepository.deleteAllByJwtExpireAndJwtDisable(true, true);
    }

    @Transactional
    public void removeCurrentJwt() {
        log.info(MessageConstants.DELETE_TOKEN, Instant.now());
        this.jwtRepository.deleteAllByJwtExpireAndJwtDisable(false, false);
    }


}
