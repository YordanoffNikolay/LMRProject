package org.yordanoffnikolay.lmrproject.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto extends UpdateUserDto {

    @NotNull(message = "Username cannot be empty.")
    @Size( min = 5, message = "Username must be at least 5 characters long.")
    private String username;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
