package com.jwtauth.learnJwtAuth.DTO;


import com.jwtauth.learnJwtAuth.model.Role;

public class UserRegisterDTO {
    private String emailId;
    private String name;
    private String password;
    private Role role;

    public UserRegisterDTO(String emailId, String password, String name, Role role) {
        this.emailId = emailId;
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public String getEmailId(){
        return emailId;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public Role getRole() {
        return role;
    }
}
