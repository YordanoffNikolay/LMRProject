package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.models.Visit;
import org.yordanoffnikolay.lmrproject.services.VisitService;

@Controller
@RequestMapping("/api/visits")
public class VisitRestController {

    private final VisitService visitService;

    public VisitRestController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/{id}")
    public Visit getVisitById(@PathVariable long id, Authentication authentication) {
        try {
            authentication.isAuthenticated();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        return visitService.getVisitById(id);
    }
}
