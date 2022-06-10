package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class AdminUserController {

    private final UserServiceImp userServiceImp;
    private final RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AdminUserController(UserServiceImp userServiceImp, RoleRepository roleRepository) {
        this.userServiceImp = userServiceImp;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin/add-user")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "add-user";
    }

    @PostMapping("/admin/add-user")
    public String addUser(@ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "role") String[] roles) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userServiceImp.saveUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String showUserList(Model model/*, ModelMap modelMap, Principal principal*/) {
        model.addAttribute("users", userServiceImp.allUsers());
        /*modelMap.addAttribute("user", userServiceImp.findByUsername(principal.getName()));*/
        return "admin";
    }
//    ///////////////////////////////////////////////////////
    @GetMapping("admin/edit/{user}")
    public String showUpdateForm(@PathVariable User user, Model model) {


        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated User user, BindingResult result, @RequestParam(value = "role") String[] roles) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        Set<Role> roleSet = userServiceImp.roleToStringArray(roles);
        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userServiceImp.saveAndFlush(user);

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
