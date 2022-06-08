package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyUser;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UsersController {
    @Autowired
    private final UserServiceImp userServiceImp;

    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }
    @GetMapping("/admin/add-user")
    public String showSignUpForm(MyUser myUser) {
        return "add-user";
    }

    @PostMapping("/admin/add-user")
    public String addUser(@Validated MyUser myUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userServiceImp.saveUser(myUser);
        return "redirect:/admin";
    }
    @GetMapping("/admin")
    public String showUserList(Model model) {
        model.addAttribute("myUsers", userServiceImp.allUsers());
        return "admin";
    }
    @GetMapping("admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long userId, Model model) {
        MyUser myUser = null;
        try {
            myUser = userServiceImp.findUserById(userId);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid myUser Id:" + userId);
        }

        model.addAttribute("myUser", myUser);
        return "update-user";
    }
    @PostMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated MyUser myUser,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            myUser.setId(id);
            return "update-user";
        }

        userServiceImp.saveUser(myUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long userId, Model model) {
        MyUser myUser = null;
        try {
            myUser = userServiceImp.findUserById(userId);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid myUser Id:" + userId);
        }
        userServiceImp.deleteById(myUser.getId());
        return "redirect:/admin";
    }

//    @GetMapping(value = "/admin")
//    public String adminPage(ModelMap modelMap, Principal principal) {
//        modelMap.addAttribute("myUser", userServiceImp.findByUsername(principal.getName()));
//        return "admin";
//    }

    @GetMapping(value = "/user")
    public String userPage(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("myUser", userServiceImp.findByUsername(principal.getName()));
        return "user";
    }

}
