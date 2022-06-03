package ru.kata.spring.boot_security.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    public final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        List<UserDetails> userDetailsList = populateUserDetails();

        for (UserDetails u : userDetailsList)
        {
            if (u.getUsername().equals(username))
            {
                return u;
            }
        }
        return null;
    }

    public List<UserDetails> populateUserDetails()
    {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList
                .add(User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build());
        userDetailsList
                .add(User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build());

        return userDetailsList;
    }

}
