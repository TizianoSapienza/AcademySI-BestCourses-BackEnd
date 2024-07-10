package com.academysi.dto;

public class UtenteLoginRequestDto {
    private String email;
    private String password;

    // Costruttori, getter e setter omessi per brevità

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}