package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.MyUser;

import java.util.List;

public interface UserService {
    MyUser findById(Long id);
    List<MyUser> findAll();
    MyUser saveUser(MyUser myUser);
    void deleteById(Long id);
}