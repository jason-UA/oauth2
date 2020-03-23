package com.example.oauth2.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeo {
    private String username;

    private String password;

    private String mobile;

    private String smsCode;

    private String email;
}
