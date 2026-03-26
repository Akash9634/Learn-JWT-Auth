package com.jwtauth.learnJwtAuth.service;

import com.jwtauth.learnJwtAuth.DTO.UserRegisterDTO;
import com.jwtauth.learnJwtAuth.model.User;
import com.jwtauth.learnJwtAuth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDTO request){

        if(userRepository.findByEmail(request.getEmailId()).isPresent()){
            throw new RuntimeException("user already exists");
        }
        User user = new User();

        user.setEmail(request.getEmailId());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);


    }
}
