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
    public List<Brick> getBricks(Authentication authentication) {
        User loggedUser = authenticationHelper.tryGetUser(authentication);
        return brickService.getBricks(loggedUser);
    }


    @PostMapping("/create")
    public void postBricks(@RequestBody BrickDto brickDto, Authentication authentication) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(authentication);
            brickService.createBrick(brickDto, loggedUser);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public void deleteBricks(@RequestBody BrickDto brickDto, Authentication authentication) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(authentication);
            brickService.deleteBrick(brickDto, loggedUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
