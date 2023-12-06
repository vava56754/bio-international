package com.perso.bio.service;

import com.perso.bio.model.user_management.User;
import com.perso.bio.repository.UserRepository;
import com.perso.bio.service.user.UserServiceImpl;
import com.perso.bio.service.validation.ValidationServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationServiceImpl validationServiceImpl;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
     void testCreatedUserValid() throws MessagingException {
        User user = new User();
        user.setUserMail("mock@gmail.com");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUserId(1);
            return savedUser;
        });
        userServiceImpl.createUser(user);
        assertNotNull(user.getUserId());
        assertEquals(1, user.getUserId());
    }

    @Test
    void testCreateUserInvalidEmail() {}
}
