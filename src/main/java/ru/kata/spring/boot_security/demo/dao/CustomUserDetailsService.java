package ru.kata.spring.boot_security.demo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.MyUser;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    final
    UserRepository userRepository;
    final
    RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return myUser;
    }

    public MyUser findUserById(Long userId) {
        Optional<MyUser> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new MyUser());
    }

    public List<MyUser> allUsers() {
        return userRepository.findAll();
    }

//    public boolean saveUser(MyUser myUser) {
//        MyUser userFromDB = userRepository.findByUsername(myUser.getUsername());
//
//        if (userFromDB != null) {
//            return false;
//        }
//        myUser.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
//        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
//        userRepository.save(myUser);
//        return true;
//    }


    public MyUser saveUser(MyUser myUser) {
        myUser.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
        return userRepository.save(myUser);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
