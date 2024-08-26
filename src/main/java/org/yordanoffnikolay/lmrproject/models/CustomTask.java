package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.Column;

import java.util.Objects;

public class CustomTask extends Task {

    @Column(name = "task_title")
    private String title;

    @Column(name = "description")
    private String description;

    public CustomTask() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomTask task = (CustomTask) o;
        return Objects.equals(description, task.description);
    }
}
