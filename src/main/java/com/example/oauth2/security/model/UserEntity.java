package com.example.oauth2.security.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,name = "user_name")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String email;

    @Column(nullable = false)
    @Enumerated
    private UserRole role;

    @Column(nullable = false, name = "a_non_expired")
    private boolean accountNonExpired;

    @Column(nullable = false, name = "non_locked")
    private boolean accountNonLocked;

    @Column(nullable = false, name = "c_non_expired")
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

}
