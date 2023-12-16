package com.example.simpleHealth.controllers;

import com.example.simpleHealth.models.Role;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.UserRepository;
import com.example.simpleHealth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, String date, Model model) {
        if (userService.createUser(user, date) == null) {
            model.addAttribute("message", "User exist!");
            return "/registration";
        }
        return "redirect:/login";
    }

}
