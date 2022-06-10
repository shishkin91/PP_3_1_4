package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findUserById(Long userId);

    List<User> allUsers();

    User saveUser(User user, String[] roles);

    boolean deleteUser(Long userId);

    User findByUsername(String username);

    void saveAndFlush(User user);
}