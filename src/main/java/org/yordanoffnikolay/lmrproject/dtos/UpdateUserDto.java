package org.yordanoffnikolay.lmrproject.dtos;

import jakarta.validation.constraints.Size;

public class UpdateUserDto {

    @Size(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    public UpdateUserDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
