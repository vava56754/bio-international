package com.perso.bio.service.validation;

import com.perso.bio.model.user_management.User;
import com.perso.bio.model.user_management.Validation;

public interface ValidationService {

    void register(User user);

    Validation readCode(String code);

    void saveDateActivation(Validation validation);
}
