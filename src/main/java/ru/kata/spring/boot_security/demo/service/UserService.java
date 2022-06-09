package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.MyUser;

import java.util.List;

public interface UserService {

    MyUser findUserById(Long userId);

    List<MyUser> allUsers();

    MyUser saveUser(MyUser myUser);

    boolean deleteUser(Long userId);

    MyUser findByUsername(String username);

}