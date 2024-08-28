package org.yordanoffnikolay.lmrproject.dtos;

import jakarta.validation.constraints.NotNull;

public class ClientDto {

    @NotNull(message = "Client name cannot be empty.")
    private String name;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
