package com.stockapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockapp.dto.AuthRequest;
import com.stockapp.dto.AuthResponse;
import com.stockapp.dto.RegisterRequest;
import com.stockapp.exception.ApiException;
import com.stockapp.exception.InvalidCredentialsException;
import com.stockapp.exception.InvalidRoleException;
import com.stockapp.exception.UserAlreadyExistsException;
import com.stockapp.exception.UserNotFoundException;
import com.stockapp.model.User;
import com.stockapp.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userrepo;

    @Autowired
    private BCryptPasswordEncoder passwordencoder;
    
    private void Authorization(String email) {
    	User user = userrepo.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User with this email is not Found"));
    	
    	if(!"ADMIN".equals(String.valueOf(user.getRole()))){
    		logger.warn("Unauthorized access attempt by the user with email: {}", email);
    		throw new InvalidRoleException("Only ADMINS are authorized to perform this action");
    	}
    	
    }

    // Logic for user to register with appropriate credentials
    @Override
    public AuthResponse register(RegisterRequest request) {
        logger.info("Registering user: {}", request.getUsername());

        if (userrepo.existsByUsername(request.getUsername())) {
            logger.warn("Username already exists: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username " + request.getUsername() + " already exists");
        } else if (userrepo.existsByEmail(request.getEmail())) {
            logger.warn("Email already exists: {}", request.getEmail());
            throw new UserAlreadyExistsException("Email " + request.getEmail() + " already exists");
        }

        if (request.getRole() == null) {
            logger.error("User registration failed: Role cannot be null");
            throw new ApiException("Role cannot be null", "ROLE_REQUIRED", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordencoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        logger.debug("Encoded password: {}", user.getPassword());

        User savedUser = userrepo.save(user);
        logger.info("User registered successfully: {}", savedUser.getUsername());

        return new AuthResponse(savedUser.getUsername(), "Registration successful", savedUser.getRole().name());
    }

    // Logic for login users with their registered details
    @Override
    public AuthResponse login(AuthRequest request) {
        logger.info("User attempting login: {}", request.getUsername());

        try {
            User user = userrepo.findByUsername(request.getUsername()).orElse(null);

            if (user == null) {
                logger.error("Login failed. Username not found: {}", request.getUsername());
                throw new UserNotFoundException(request.getUsername());
            }

            if (user.getPassword() == null) {
                logger.error("Stored password is null for user: {}", request.getUsername());
                throw new ApiException("Invalid stored password", "INVALID_DATA", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (!passwordencoder.matches(request.getPassword(), user.getPassword())) {
                logger.error("Login failed. Invalid credentials for user: {}", request.getUsername());
                throw new InvalidCredentialsException("Invalid credentials");
            }

            if (user.getRole() == null) {
                logger.error("User role is null: {}", request.getUsername());
                throw new ApiException("User role is missing", "ROLE_MISSING", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            logger.info("User logged in successfully: {}", user.getUsername());
            return new AuthResponse(user.getUsername(), "Login successful", user.getRole().name());

        } catch (Exception e) {
            logger.error("Unexpected error during login", e);
            throw new ApiException("Login failed due to internal error", "INTERNAL_LOGIN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Logic to update the user
//    public User updateUserById(Long id, RegisterRequest updatedUser, String email) {
//        logger.info("Attempting to update user with ID: {}", id);
//
//        User existingUser = userrepo.findById(id).orElse(null);
//
//        if (existingUser == null) {
//            logger.warn("User not found with ID: {}", id);
//            throw new ApiException("User not found", "NOT_FOUND", HttpStatus.NOT_FOUND);
//        }
//
////        if (!"ADMIN".equals(existingUser.getRole().name())) {
////            logger.warn("User with ID {} does not have permission to update", id);
////            throw new InvalidRoleException("Access denied: only ADMIN users can perform this update.");
////        }
//
//        if (updatedUser.getUsername() != null) {
//            existingUser.setUsername(updatedUser.getUsername());
//        }
//        if (updatedUser.getEmail() != null) {
//            existingUser.setEmail(updatedUser.getEmail());
//        }
//        if (updatedUser.getPassword() != null) {
//            existingUser.setPassword(encodePassword(updatedUser.getPassword()));
//            
//        }
//        
//        
//        //updated
//        User maybeadmin = userrepo.findByEmail(email).orElseThrow(()-> new InvalidRoleException("Only adminsa can able to update this field"));
//        if (updatedUser.getRole() != null) {
//        	Authorization(maybeadmin.getEmail());
//            existingUser.setRole(updatedUser.getRole());
//        }
//
//        User updated = userrepo.save(existingUser);
//        logger.info("User updated successfully: {}", updated.getUsername());
//
//        return updated;
//    }
    public User updateUserById(Long id, RegisterRequest updatedUser, String requesterEmail) {
        logger.info("User update requested by: {}", requesterEmail);

        // 1. Get the requester info
        User requester = userrepo.findByEmail(requesterEmail)
            .orElseThrow(() -> new UserNotFoundException("Requester not found"));

        boolean isAdmin = "ADMIN".equals(requester.getRole().name());
        System.out.println(requester.getRole().name());

        // 2. Get the target user to update
        User existingUser = userrepo.findById(id)
            .orElseThrow(() -> new ApiException("User not found", "NOT_FOUND", HttpStatus.NOT_FOUND));

        // 3. Check permissions
        if (!isAdmin) {
            // Non-admin trying to update someone else
            if (!requester.getId().equals(id)) {
                logger.warn("Unauthorized update attempt by non-admin user: {}", requesterEmail);
                throw new InvalidRoleException("You are not authorized to update other users.");
            }

            // Non-admin trying to change role
            if (updatedUser.getRole() != null) {
                logger.warn("Non-admin user {} attempted to change role", requesterEmail);
                throw new InvalidRoleException("Only admins can update the role field.");
            }
        }

        // 4. Perform updates (based on what fields are not null)
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(encodePassword(updatedUser.getPassword()));
        }

        if (isAdmin && updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        User updated = userrepo.save(existingUser);
        logger.info("User updated successfully: {}", updated.getUsername());

        return updated;
    }


    // Logic to encode the password for security
    public String encodePassword(String rawPassword) {
        logger.debug("Encoding password");
        return passwordencoder.encode(rawPassword);
    }
}