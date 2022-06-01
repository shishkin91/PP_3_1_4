package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }
    @Transactional
    public User saveUser(User user) {
        return userDao.save(user);
    }
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

}
