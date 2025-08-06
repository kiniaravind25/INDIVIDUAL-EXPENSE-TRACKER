package com.project.expenseTracker.controller;


import com.project.expenseTracker.dto.LoginRequest;
import com.project.expenseTracker.dto.RegisterRequest;
import com.project.expenseTracker.exception.UserNotFoundException;
import com.project.expenseTracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) throws UserNotFoundException {
        String message = authService.registerUser(request);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) throws UserNotFoundException {
        String message = authService.loginUser(request);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
