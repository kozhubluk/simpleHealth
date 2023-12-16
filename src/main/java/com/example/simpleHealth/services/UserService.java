package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Role;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Map<String, String> form, User user) {
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

    public void updateUser(String birthday, String fullname, User user) {
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

    public User readUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user, String date) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        }
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(true);
        if (Objects.equals(user.getUsername(), "Adminka"))
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN)));
        else user.setRoles(Collections.singleton(Role.USER));

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date b = formatter.parse(date);
            user.setBirthday(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userRepository.save(user);
        return user;
    }
}
