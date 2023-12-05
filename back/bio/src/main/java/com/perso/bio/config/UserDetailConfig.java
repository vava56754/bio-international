package com.perso.bio.config;

import com.perso.bio.service.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailConfig {

    public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
    }
}
