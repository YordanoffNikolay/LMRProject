package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.WorkplaceDto;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Workplace;
import org.yordanoffnikolay.lmrproject.services.BrickService;
import org.yordanoffnikolay.lmrproject.services.WorkplaceService;

import java.util.List;

@RestController()
@RequestMapping("/api/workplaces")
public class WorkplaceRestController {


    private final WorkplaceService workplaceService;
    private final AuthenticationHelper authenticationHelper;
    private final BrickService brickService;

    public WorkplaceRestController(WorkplaceService workplaceService, AuthenticationHelper authenticationHelper,
                                   BrickService brickService) {
        this.workplaceService = workplaceService;
        this.authenticationHelper = authenticationHelper;
        this.brickService = brickService;
    }

    @GetMapping
    public List<Workplace> getAll(){
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
        return workplaceService.getAll();
    }

    @GetMapping("/{id}")
    public Workplace getById(@PathVariable Long id) {
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
            return workplaceService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
            workplaceService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public void create(@RequestBody WorkplaceDto workplaceDto) {
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
            Workplace workplace = new Workplace();
            workplace.setName(workplaceDto.getName());
            workplace.setAddress(workplaceDto.getAddress());
            brickService.getBrickByName(workplaceDto.getBrickName()).ifPresent(workplace::setBrick);
            workplaceService.create(workplace);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Workplace update(@PathVariable Long id, @RequestBody WorkplaceDto workplaceDto) {
        try {
            authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication());
            return workplaceService.update(id, workplaceDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + e.getMessage());
        }
    }
}
