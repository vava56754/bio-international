package com.perso.bio.config;


import com.perso.bio.enums.TypeDeRole;
import com.perso.bio.repository.UserRepository;
import com.perso.bio.service.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authorize ->
                                authorize.requestMatchers(HttpMethod.POST, "/user/sign").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/user/activation").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/files/{filename:.+}").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/product/{id}").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/product/all").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/product/type/{typeId}/body/{bodyId}").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/house/{id}").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/house/all").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/type/all").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/body/all").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/user/update").hasAnyRole(TypeDeRole.ADMIN.name(), TypeDeRole.USER.name())
                                        .requestMatchers(HttpMethod.POST, "/user/logout").hasAnyRole(TypeDeRole.ADMIN.name(), TypeDeRole.USER.name())
                                        .requestMatchers("/house/**", "/product/**", "/type/**", "/body/**", "/user/**").hasRole(TypeDeRole.ADMIN.name())
                                        .anyRequest().authenticated())
                        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                        )
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
