package com.project.expensetracker.service;


import com.project.expensetracker.dto.LoginRequest;
import com.project.expensetracker.dto.RegisterRequest;
import com.project.expensetracker.exception.RegistrationException;
import com.project.expensetracker.exception.UserNotFoundException;
import com.project.expensetracker.model.Role;
import com.project.expensetracker.model.User;
import com.project.expensetracker.repository.RoleRepository;
import com.project.expensetracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Email already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RegistrationException("ROLE_USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return "User registered successfully";
    }

    public String loginUser(LoginRequest request) throws UserNotFoundException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        var decryptedPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!decryptedPassword) {
            throw new UserNotFoundException("Invalid credentials");
        }

        return "Login successful";
    }
}
