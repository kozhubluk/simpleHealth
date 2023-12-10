package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Role;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void userEdit(Map<String, String> form, User user) {
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        if (user.getRoles() != null) {
            user.getRoles().clear();
        }

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public void userEdit(String birthday, String fullname, User user) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date b = formatter.parse(birthday);
            user.setBirthday(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setFullName(fullname);
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }
}
