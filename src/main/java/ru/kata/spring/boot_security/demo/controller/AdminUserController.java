package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class AdminUserController {
    @Autowired
    private final UserServiceImp userServiceImp;

    public AdminUserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/admin/add-user")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/admin/add-user")
    public String addUser(@Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String showUserList(Model model) {
        model.addAttribute("users", userServiceImp.allUsers());
        return "admin";
    }

    @GetMapping("admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long userId, Model model) {
        User user = null;
        try {
            user = userServiceImp.findUserById(userId);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user Id:" + userId);
        }

        model.addAttribute("user", user);
        model.addAttribute("roles", user.getAuthorities());
        return "update-user";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long userId, Model model) {
        User user = null;
        try {
            user = userServiceImp.findUserById(userId);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid user Id:" + userId);
        }
        userServiceImp.deleteUser(user.getId());
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String userPage(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userServiceImp.findByUsername(principal.getName()));
        return "user";
    }

}
