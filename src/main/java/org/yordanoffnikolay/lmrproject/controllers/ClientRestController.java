package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.models.Client;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @GetMapping
    public List<Client> getAll() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
