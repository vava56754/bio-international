package com.perso.bio.controller;


import com.perso.bio.constants.Field;
import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.UserDTO;
import com.perso.bio.service.jwt.JwtService;
import com.perso.bio.dto.AuthenticationDTO;
import com.perso.bio.model.user_management.User;
import com.perso.bio.service.user.UserService;


import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    @Autowired
    UserController(@Qualifier("jpa") UserService userService, @Qualifier("jpa") JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/sign")
    public ResponseEntity<String> sign(@RequestBody User user) throws MessagingException {
        this.userService.createUser(user);
        return new ResponseEntity<>(MessageConstants.SIGN_IN, HttpStatus.CREATED);
    }

    @PostMapping(path = "/activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        this.userService.activation(activation);
        return new ResponseEntity<>(Field.ACTIVATE, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("permitAll()")
    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO) throws AuthenticationException {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generate(authenticationDTO.username()).get(Field.BEARER);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put(Field.BEARER, token);

            return ResponseEntity.ok().headers(headers).body(responseMap);
        }

        return null;
    }

    @PutMapping(path = "/update/password")
    public ResponseEntity<String>updatePassword(@RequestParam String updatePassword, @RequestParam String password) throws InvalidAttributeValueException {
        this.userService.updatePassword(updatePassword, password);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        this.jwtService.logout();
        return ResponseEntity.ok().body(Field.LOGOUT);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<String> setUserInfo(@RequestBody UserDTO user) {
        this.userService.updateUserInfo(user);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> userAll() {
        List<User> users = this.userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<User> getUserById() {
        User user = this.userService.getUserById();
        return ResponseEntity.ok(user);
    }


}
