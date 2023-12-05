package com.perso.bio.controller;


import com.perso.bio.constants.Field;
import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.UserDTO;
import com.perso.bio.service.jwt.JwtService;
import com.perso.bio.dto.AuthenticationDTO;
import com.perso.bio.model.user_management.User;
import com.perso.bio.service.user.UserService;

import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> sign(@RequestBody User user) {
        this.userService.createUser(user);
        return new ResponseEntity<>(MessageConstants.SIGN_IN, HttpStatus.CREATED);
    }

    @PostMapping(path = "/activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        this.userService.activation(activation);
        return new ResponseEntity<>(Field.ACTIVATE, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));
        if (authenticate.isAuthenticated()) {
            return this.jwtService.generate(authenticationDTO.username());
        }
        return Collections.emptyMap();
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        this.jwtService.logout();
        return new ResponseEntity<>(Field.LOGOUT, HttpStatus.OK);
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


}
