package com.jwtauth.learnJwtAuth.DTO;

public class LoginDTO {
    private String emailId;
    private String password;

    public LoginDTO(String emailId, String password){
        this.emailId = emailId;
        this.password = password;
    }

    public LoginDTO(){}

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
