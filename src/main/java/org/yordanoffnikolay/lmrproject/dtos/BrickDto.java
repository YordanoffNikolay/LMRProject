package org.yordanoffnikolay.lmrproject.dtos;

import jakarta.validation.constraints.NotNull;

public class BrickDto {

    @NotNull(message = "Brick name cannot be empty.")
    private String name;

    public BrickDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
