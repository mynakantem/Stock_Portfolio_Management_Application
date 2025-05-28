package com.stockapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.exception.InvalidCredentialsException;
import com.stockapp.exception.UserAlreadyExistsException;
import com.stockapp.exception.UsernameNotFoundException;
import com.stockapp.model.User;
import com.stockapp.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private BCryptPasswordEncoder passwordencoder;
    

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userrepo.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username " + request.getUsername() + " already exists");
        } else if (userrepo.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email " + request.getEmail() + " already exists");
        } else {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordencoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            User savedUser = userrepo.save(user);
            return new AuthResponse(savedUser.getUsername(), "Registration successful", savedUser.getRole().name());
        }
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userrepo.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(request.getUsername());
        } else if (!passwordencoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        } else {
            return new AuthResponse(user.getUsername(), "Login successful", user.getRole().name());
        }
    }

    public String encodePassword(String rawPassword) {
        return passwordencoder.encode(rawPassword);
    }
}
