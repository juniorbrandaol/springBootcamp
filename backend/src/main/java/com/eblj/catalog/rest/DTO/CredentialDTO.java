package com.eblj.catalog.rest.DTO;

public class CredentialDTO {
    private String email;
    private String password;

    public CredentialDTO(){}

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
