package com.jwtauth.learnJwtAuth.service;

import com.jwtauth.learnJwtAuth.DTO.LoginDTO;
import com.jwtauth.learnJwtAuth.DTO.LoginResponse;
import com.jwtauth.learnJwtAuth.DTO.UserRegisterDTO;
import com.jwtauth.learnJwtAuth.model.User;
import com.jwtauth.learnJwtAuth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    public LoginResponse loginUser(LoginDTO request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmailId(),
                            request.getPassword()
                    )
            );


            User user = (User) authentication.getPrincipal();

            String token = jwtService.generateToken(user);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setEmail(user.getEmail());

        }
        catch(AuthenticationException e){
            throw new RuntimeException("wrong credentials");
        }



    }
}
