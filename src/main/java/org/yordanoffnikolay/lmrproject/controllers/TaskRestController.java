package org.yordanoffnikolay.lmrproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.*;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Task;
import org.yordanoffnikolay.lmrproject.services.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private final AuthenticationHelper authenticationHelper;
    private final TaskService taskService;
    private final ObjectMapper jacksonObjectMapper;

    public TaskRestController(AuthenticationHelper authenticationHelper, TaskService taskService, ObjectMapper jacksonObjectMapper) {
        this.authenticationHelper = authenticationHelper;
        this.taskService = taskService;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @PostMapping
    public Task create(@RequestBody GenericTaskDto genericTaskDTO) {
        String taskType = genericTaskDTO.getTaskType().toLowerCase();
        try {
            authenticationHelper.isAuthenticated();
            switch (taskType) {
                case "visit":
                    VisitDto visitDto = jacksonObjectMapper.convertValue(genericTaskDTO.getTaskData(), VisitDto.class);
                    return taskService.createTask(visitDto);
                case "officework":
                    OfficeWorkDto officeWorkDto = jacksonObjectMapper.convertValue(genericTaskDTO.getTaskData(), OfficeWorkDto.class);
                    return taskService.createTask(officeWorkDto);
                case "customtask":
                    CustomTaskDto customTaskDto = jacksonObjectMapper.convertValue(genericTaskDTO.getTaskData(), CustomTaskDto.class);
                    return taskService.createTask(customTaskDto);
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown task type");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to login first");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
            return taskService.getTaskById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
    }
}