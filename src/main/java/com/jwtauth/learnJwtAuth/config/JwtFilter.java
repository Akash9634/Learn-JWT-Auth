package com.jwtauth.learnJwtAuth.config;

import com.jwtauth.learnJwtAuth.model.User;
import com.jwtauth.learnJwtAuth.repository.UserRepository;
import com.jwtauth.learnJwtAuth.service.CustomUserDetailService;
import com.jwtauth.learnJwtAuth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailService customUserDetailService;

    public JwtFilter(JwtService jwtService, CustomUserDetailService customUserDetailService){
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get authheader
        String authHeader = request.getHeader("Authorization");

        //check if header exists
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //get token now
        String token = authHeader.substring(7);

        //extract email from token
        String email = jwtService.extractEmail(token);

        //check if email exists and not already authenticated
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = (User) customUserDetailService.loadUserByUsername(email); // (User) to tell compiler that the User iam using is the entity that implementing UserDetails otherwise compiler may think its Springs default User, or we can just write UserDetails user = ....

            if(jwtService.isTokenValid(token, user)){

                //create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                //tell spring security, user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
