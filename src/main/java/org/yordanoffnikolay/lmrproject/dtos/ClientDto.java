package org.yordanoffnikolay.lmrproject.dtos;

import jakarta.validation.constraints.NotNull;

public class ClientDto {

    @NotNull(message = "Client name cannot be empty.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
