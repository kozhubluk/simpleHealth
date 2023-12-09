package com.example.simpleHealth.Services;

import com.example.simpleHealth.models.Role;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.UserRepository;
import com.example.simpleHealth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testList() {
        // Arrange
        List<User> users = new ArrayList<>(Arrays.asList(new User(), new User()));
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.list();

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testUserEditWithRoles() {
        // Arrange
        User user = new User();
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_ADMIN", "on");
        form.put("ROLE_USER", "on");

        // Act
        userService.userEdit("username", form, user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUserEditWithBirthdayAndFullName() {
        // Arrange
        User user = new User();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = "1990-01-01";
        String fullname = "John Doe";

        // Act
        userService.userEdit(birthday, fullname, user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserByPrincipal() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(new User());

        // Act
        User result = userService.getUserByPrincipal(principal);

        // Assert
        assertEquals(new User(), result);
    }
}