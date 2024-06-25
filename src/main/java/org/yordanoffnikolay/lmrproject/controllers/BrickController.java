package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.services.BrickService;

@RestController
@RequestMapping("/api/bricks")
public class BrickController {

    private final AuthenticationHelper authenticationHelper;
    private final BrickService brickService;

    public BrickController(AuthenticationHelper authenticationHelper, BrickService brickService) {
        this.authenticationHelper = authenticationHelper;
        this.brickService = brickService;
    }

    @GetMapping
    public String getBricks() {
        return "Bricks";
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
}
