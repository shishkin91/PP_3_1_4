package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.MyUser;

import java.util.List;

public interface UserService {

    MyUser findUserById(Long userId);
    List<MyUser> findAll();
    MyUser saveUser(MyUser myUser);
    void deleteById(Long id);

//    ////////////////////////////////////////////////////////////

//    MyUser findById(Long id);
//    List<MyUser> findAll();
//    MyUser saveUser(MyUser myUser);
//    void deleteById(Long id);
}