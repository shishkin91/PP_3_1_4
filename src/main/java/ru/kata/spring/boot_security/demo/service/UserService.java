package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> allUser();

    User getUserById(long id);

    void removeUserById(long id);

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User createOrUpdate(User user);
}
