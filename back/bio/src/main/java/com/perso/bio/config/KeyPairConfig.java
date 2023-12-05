package com.perso.bio.config;

import com.perso.bio.constants.Field;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.NoSuchAlgorithmException;

@Service
public class KeyPairConfig {

    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Field.RSA);
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
