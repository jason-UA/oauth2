package com.example.oauth2.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;

    private String password;

    private String mobileNumber;

    private String email;
}
