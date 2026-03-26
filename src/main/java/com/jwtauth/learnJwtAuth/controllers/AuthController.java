package com.jwtauth.learnJwtAuth.controllers;

import com.jwtauth.learnJwtAuth.DTO.UserRegisterDTO;
import com.jwtauth.learnJwtAuth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(UserRegisterDTO request){
        authService.registerUser(request);
        return ResponseEntity.ok("user registered successfully");
    }
}
