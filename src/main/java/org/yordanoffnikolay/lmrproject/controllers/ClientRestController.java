package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    private final AuthenticationHelper authenticationHelper;
    private final ClientService clientService;

    public ClientRestController(AuthenticationHelper authenticationHelper, ClientService clientService) {
        this.authenticationHelper = authenticationHelper;
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAll() {
        try {
            authenticationHelper.isAuthenticated();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable long id) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        return clientService.getClientById(id).orElseThrow();
    }

    @PostMapping
    public void createClient(@RequestBody ClientDto clientDto) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        clientService.createClient(clientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable long id, @RequestBody ClientDto clientDto) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        clientService.updateClient(clientDto, id);
    }
}
