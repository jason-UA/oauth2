package com.example.oauth2.security.repository;

import com.example.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

    User findByEmail(String email);

    User findByMobileNumber(String number);

}
