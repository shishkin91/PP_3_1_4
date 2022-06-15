package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("userAuthorized", user);
        model.addAttribute("listUsers", userService.allUser());
        model.addAttribute("newUser", new User());
        List<Role> listRoles = roleService.allRole();
        model.addAttribute("listRoles", listRoles);
        return "admin";
    }

    @PostMapping("/admin/create")
    public String addUser(@RequestParam("idRoles") List<Long> idRoles,
                          User user) {
        Set<Role> roleList = new HashSet<>();
        for (Long id : idRoles) {
            roleList.add(roleService.findRoleById(id));
        }
        user.setRoles(roleList);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("idRoles") List<Long> rolesId) {
        Set<Role> listRoles = new HashSet<>();
        for (Long idRole : rolesId) {
            listRoles.add(roleService.findRoleById(idRole));
        }
        user.setRoles(listRoles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }
}
