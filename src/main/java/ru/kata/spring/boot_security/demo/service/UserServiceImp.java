package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.model.MyUser;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final CustomUserDetailsService dao;
    private final UserRepository userRepository;//

    public UserServiceImp(CustomUserDetailsService dao, UserRepository userRepository) {
        this.dao = dao;//
        this.userRepository = userRepository;
    }
    @Transactional
    public MyUser findUserById(Long userId){//+
        return dao.findUserById(userId);
    }
    public List<MyUser> allUsers() {//+
        return dao.allUsers();
    }
    @Transactional
    public boolean saveUser(MyUser myUser) {//+
        return dao.saveUser(myUser);
    }
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


//    /////////////////////////////////////////////////////////////////

//    private final UserRepository userRepository;
//
//    public UserServiceImp(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    @Transactional
//    public MyUser findById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//    public List<MyUser> findAll() {
//        return (List<MyUser>) userRepository.findAll();
//    }
//    @Transactional
//    public MyUser saveUser(MyUser myUser) {
//        return userRepository.save(myUser);
//    }
//    @Transactional
//    public void deleteById(Long id) {
//        userRepository.deleteById(id);
//    }
//    @Transactional
//    public MyUser findByUsername(String username){
//        return userRepository.findByUsername(username);
//    }


}
