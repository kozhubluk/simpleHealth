package com.example.simpleHealth.controllers;

import com.example.simpleHealth.models.Role;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String admin(Model model) {
        return "admin";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        User user = userService.getUserByPrincipal(principal);
        Date date = user.getBirthday() != null ? user.getBirthday() : new Date();
        model.addAttribute("user", user);
        model.addAttribute("birthday", formatter.format(date));
        return "profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.readUsers());
        return "user-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/edit")
    public String userSave(
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.updateUser(form, user);

        return "redirect:/users";
    }

    @PostMapping("/users/put")
    public String userSave(@RequestParam("birthday") String birthday,
                           @RequestParam("fullname") String fullname,
                           @RequestParam("userId") User user
    ) {
        userService.updateUser(birthday, fullname, user);
        return "redirect:/";
    }

}
