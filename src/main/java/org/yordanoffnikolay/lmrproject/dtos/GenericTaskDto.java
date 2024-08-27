package org.yordanoffnikolay.lmrproject.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;

public class GenericTaskDto {

    @NotNull(message = "You need to specify task type.")
    private String taskType;
    private JsonNode taskData;

    public String getTaskType() {
        return taskType;
    }

    public JsonNode getTaskData() {
        return taskData;
    }

    public void setTaskData(JsonNode taskData) {
        this.taskData = taskData;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
