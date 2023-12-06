package com.perso.bio.service.user;

import com.perso.bio.dto.UserDTO;
import com.perso.bio.model.user_management.User;
import jakarta.mail.MessagingException;

import javax.naming.directory.InvalidAttributeValueException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers();

    void createUser(User user) throws MessagingException;

    void activation(Map<String, String> activation);

    void updatePassword(String updatePassword, String oldPassword) throws InvalidAttributeValueException;

    User getUserById();

    void updateUserInfo(UserDTO user);


}
