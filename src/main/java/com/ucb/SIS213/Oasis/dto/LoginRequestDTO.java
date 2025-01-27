package com.ucb.SIS213.Oasis.dto;

public class LoginRequestDTO {
    private String correo;
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    //dame getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    //dame getters y setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //toString
    @Override
    public String toString() {
        return "LoginRequestDTO [password=" + password + ", usuario=" + correo + "]";
    }
}
