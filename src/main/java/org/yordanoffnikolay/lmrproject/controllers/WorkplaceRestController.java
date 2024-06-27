package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yordanoffnikolay.lmrproject.models.Workplace;
import org.yordanoffnikolay.lmrproject.services.WorkplaceService;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/workplaces")
public class WorkplaceRestController {


    private final WorkplaceService workplaceService;

    public WorkplaceRestController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping
    public List<Workplace> getAll() {
        //todo
        return new ArrayList<>();
    }
}
