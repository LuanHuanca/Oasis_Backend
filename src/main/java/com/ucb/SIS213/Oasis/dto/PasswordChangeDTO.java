package com.ucb.SIS213.Oasis.dto;

public class PasswordChangeDTO {
    private String password;

    public PasswordChangeDTO() {}

    public PasswordChangeDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
