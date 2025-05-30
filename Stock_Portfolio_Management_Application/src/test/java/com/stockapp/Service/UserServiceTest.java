package com.stockapp.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.model.Role;
import com.stockapp.model.User;
import com.stockapp.repository.UserRepository;
import com.stockapp.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepository userrepo;

    @Mock
    private BCryptPasswordEncoder passwordencoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("user1");
        req.setEmail("user1@example.com");
        req.setPassword("password");
        req.setRole(Role.USER);

        when(userrepo.existsByUsername("user1")).thenReturn(false);
        when(userrepo.existsByEmail("user1@example.com")).thenReturn(false);
        when(passwordencoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("user1");
        savedUser.setEmail("user1@example.com");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.USER);

        when(userrepo.save(any(User.class))).thenReturn(savedUser);

        var response = userService.register(req);

        assertEquals("user1", response.getUsername());
        assertEquals("USER", response.getRole()); 
        assertEquals("Registration successful", response.getMessage());
    }

    @Test
    void testLogin_Success() {
        AuthRequest req = new AuthRequest();
        req.setUsername("user1");
        req.setPassword("password");

        User user = new User();
        user.setUsername("user1");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        when(userrepo.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordencoder.matches("password", "encodedPassword")).thenReturn(true);

        var response = userService.login(req);

        assertEquals("user1", response.getUsername());
        assertEquals("USER", response.getRole());
        assertEquals("Login successful", response.getMessage());
    }
}