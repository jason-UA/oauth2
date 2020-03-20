package com.example.oauth2.security.service;

import com.example.oauth2.security.model.User;
import com.example.oauth2.security.model.UserRole;
import com.example.oauth2.security.model.UserEntity;
import com.example.oauth2.security.repository.UserRepository;
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

    public UserEntity insertUser(User user) {
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .role(UserRole.user)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        log.info("CREATE USER: {}",  savedUser);
        return savedUser;
    }


    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        log.info("FIND USER: {}", user);
        return user;
    }

    public UserEntity getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        log.info("FIND USER: {}", user);
        return user;
    }

    public UserEntity getUserByMobileNumber(String number) {
        UserEntity user = userRepository.findByMobileNumber(number);
        log.info("FIND USER: {}", user);
        return user;
    }

}
