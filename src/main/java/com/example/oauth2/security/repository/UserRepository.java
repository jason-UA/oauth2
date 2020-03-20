package com.example.oauth2.security.repository;

import com.example.oauth2.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String name);

    UserEntity findByEmail(String email);

    UserEntity findByMobileNumber(String number);

}
