package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Brick;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.services.BrickService;

import java.util.List;


@RestController
@RequestMapping("/api/bricks")
public class BrickRestController {

    private final AuthenticationHelper authenticationHelper;
    private final BrickService brickService;

    public BrickRestController(AuthenticationHelper authenticationHelper, BrickService brickService) {
        this.authenticationHelper = authenticationHelper;
        this.brickService = brickService;
    }

    @GetMapping
    public List<Brick> getBricks() {
        try {
            authenticationHelper.isAuthenticated();
            return brickService.getBricks();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to login first");
        }
    }

    @GetMapping("/{id}")
    public Brick getBrickById(@PathVariable long id) {
        try {
            authenticationHelper.isAuthenticated();
            return brickService.getBrick(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to login first");
        }
    }

    @PostMapping("/create")
    public void createBricks(@RequestBody BrickDto brickDto) {
        try {
            authenticationHelper.isAuthenticated();
            brickService.createBrick(brickDto);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBricks(@PathVariable long id, Authentication authentication) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(authentication);
            brickService.deleteBrick(id, loggedUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateBricks(@PathVariable long id, @RequestBody BrickDto brickDto, Authentication authentication) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(authentication);
            brickService.updateBrick(id, brickDto, loggedUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
