package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.model.MyUser;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final CustomUserDetailsService dao;

    public UserServiceImp(CustomUserDetailsService dao) {
        this.dao = dao;
    }

    @Transactional
    public MyUser findUserById(Long userId) {//+
        return dao.findUserById(userId);
    }

    public List<MyUser> allUsers() {//+
        return dao.allUsers();
    }

    public MyUser saveUser(MyUser myUser) {
        return dao.saveUser(myUser);
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        return dao.deleteUser(userId);
    }

    @Transactional
    public MyUser findByUsername(String username) {
        return dao.findByUsername(username);
    }

}
