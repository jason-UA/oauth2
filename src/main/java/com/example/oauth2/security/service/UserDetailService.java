package com.example.oauth2.security.service;

import com.example.oauth2.security.model.UserDeo;
import com.example.oauth2.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getUserByUsername(username);
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().name()));
        return userDetails;
    }


    public UserDetails loadUserByMobileNumber(String mobileNumber) throws UsernameNotFoundException {
        UserEntity user = userService.getUserByMobileNumber(mobileNumber);
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().name()));
        return userDetails;
    }

    public UserDetails insertUserByUser(UserDeo userDeo) throws UsernameNotFoundException {
        UserEntity user = userService.insertUser(userDeo);
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().name()));
        return userDetails;
    }

}