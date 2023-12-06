package com.perso.bio.service.user;


import com.perso.bio.constants.Field;
import com.perso.bio.constants.MessageConstants;
import com.perso.bio.dto.UserDTO;
import com.perso.bio.enums.TypeDeRole;
import com.perso.bio.model.user_management.Role;
import com.perso.bio.model.user_management.User;
import com.perso.bio.model.user_management.Validation;
import com.perso.bio.repository.UserRepository;
import com.perso.bio.service.validation.ValidationService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributeValueException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Qualifier("jpa")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ValidationService validationService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void createUser(User user) throws MessagingException {
        this.validateMail(user.getUserMail());
        Optional<User> userOptional = this.userRepository.findByUserMail(user.getUserMail());

        if (userOptional.isPresent()) {
            throw new DuplicateKeyException(MessageConstants.USER_ALREADY_EXIST);
        }

        String mdp = this.passwordEncoder.encode(user.getPassword());
        user.setUserPassword(mdp);

        Role roleUser = new Role(TypeDeRole.USER);
        user.setRole(roleUser);

        user = this.userRepository.save(user);
        this.validationService.register(user);
    }

    @Override
    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.readCode(activation.get(Field.CODE));
        if (Instant.now().isAfter(validation.getExpire())) {
            throw new RuntimeException(MessageConstants.CODE_EXPIRE);
        }
        User user = this.userRepository.findById(validation.getUser().getUserId()).orElseThrow(() ->
                new EntityNotFoundException(MessageConstants.USER_UNKNOWN));
        user.setEnable(true);
        this.validationService.saveDateActivation(validation);
        this.userRepository.save(user);

    }

    @Override
    public void updatePassword(String updatePassword, String oldPassword) throws InvalidAttributeValueException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            String newPassword = this.passwordEncoder.encode(updatePassword);
            user.setUserPassword(newPassword);
            this.userRepository.save(user);
        } else {
            throw new InvalidAttributeValueException("wrong password");
        }

    }

    @Override
    public User getUserById() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(user.getUserId()).orElseThrow(() ->
                new EntityNotFoundException(MessageConstants.USER_SERVICE_ERROR_MESSAGE + user.getUserId()));
    }

    @Override
    public void updateUserInfo(UserDTO user) {
        User userSave = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userSave.setUserName(Optional.ofNullable(user.getUserName()).orElse(userSave.getUserName()));
        userSave.setUserMail(Optional.ofNullable(user.getUserMail()).orElse(userSave.getUserMail()));
        userSave.setUserFirstName(Optional.ofNullable(user.getUserFirstName()).orElse(userSave.getUserFirstName()));
        userSave.setUserPhone(Optional.ofNullable(user.getUserPhone()).orElse(userSave.getUserPhone()));
        userSave.setUserAddress(Optional.ofNullable(user.getUserAddress()).orElse(userSave.getUserAddress()));
        userSave.setUserPostalCode(Optional.ofNullable(user.getUserPostalCode()).orElse(userSave.getUserPostalCode()));
        userSave.setUserCity(Optional.ofNullable(user.getUserCity()).orElse(userSave.getUserCity()));
        userSave.setUserCountry(Optional.ofNullable(user.getUserCountry()).orElse(userSave.getUserCountry()));

        this.userRepository.save(userSave);

    }

    private void validateMail(String userMail) {
        if (!userMail.contains("@") || !userMail.contains(".")) {
            throw (new IllegalArgumentException());
        }
    }
}
