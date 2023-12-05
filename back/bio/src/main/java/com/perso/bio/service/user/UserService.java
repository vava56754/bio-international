package com.perso.bio.service.user;

import com.perso.bio.dto.UserDTO;
import com.perso.bio.model.user_management.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers();

    void createUser(User user);

    void activation(Map<String, String> activation);

    void updateUserInfo(UserDTO user);
}
