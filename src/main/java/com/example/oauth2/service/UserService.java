package com.example.oauth2.service;

import com.example.oauth2.model.UserDeo;
import com.example.oauth2.model.UserRole;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User insertUser(UserDeo userDeo) {
        User user = User.builder()
                .username(userDeo.getUsername())
                .password(passwordEncoder.encode(userDeo.getPassword()))
                .email(userDeo.getEmail())
                .mobileNumber(userDeo.getMobile())
                .role(UserRole.user)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);
        log.info("CREATE USER: {}",  savedUser);
        return savedUser;
    }


    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("FIND USER: {}", user);
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        log.info("FIND USER: {}", user);
        return user;
    }

    public User getUserByMobileNumber(String number) {
        User user = userRepository.findByMobileNumber(number);
        log.info("FIND USER: {}", user);
        return user;
    }

}
