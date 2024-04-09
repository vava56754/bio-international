package com.perso.bio.service.validation;

import com.perso.bio.model.user_management.User;
import com.perso.bio.model.user_management.Validation;
import jakarta.mail.MessagingException;

public interface ValidationService {

    void register(User user) throws MessagingException;

    Validation readCode(String code);

    void saveDateActivation(Validation validation);
}
