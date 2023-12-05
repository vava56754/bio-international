package com.perso.bio.service.validation;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.user_management.User;
import com.perso.bio.model.user_management.Validation;
import com.perso.bio.repository.ValidationRepository;
import com.perso.bio.service.notification_user.NotificationUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@Qualifier("jpa")
public class ValidationServiceImpl implements ValidationService {

    Random random = new Random();

    private final ValidationRepository validationRepository;

    private final NotificationUserService notificationUserService;

    @Autowired
    public ValidationServiceImpl(ValidationRepository validationRepository, NotificationUserService notificationUserService) {
        this.validationRepository = validationRepository;
        this.notificationUserService = notificationUserService;
    }

    public void register(User user) {
        Validation validation = new Validation();
        validation.setUser(user);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expire = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpire(expire);

        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);

        this.validationRepository.save(validation);
        this.notificationUserService.sendAccountConfirmationNotification(validation);

    }

    public Validation readCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(MessageConstants.CODE_INVALID));
    }

    public void saveDateActivation(Validation validation) {
        validation.setActivation(Instant.now());
        validationRepository.save(validation);
    }

}
