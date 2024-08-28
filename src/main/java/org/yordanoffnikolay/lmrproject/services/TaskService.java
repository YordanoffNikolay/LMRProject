package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.CustomTaskDto;
import org.yordanoffnikolay.lmrproject.dtos.OfficeWorkDto;
import org.yordanoffnikolay.lmrproject.dtos.VisitDto;
import org.yordanoffnikolay.lmrproject.models.Task;

public interface TaskService {
    Task createTask(VisitDto visitDto);
    Task createTask(OfficeWorkDto officeWorkDto);
    Task createTask(CustomTaskDto customTaskDto);
    Task getTaskById(long taskId);
}
