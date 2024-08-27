package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.DateDto;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Workday;
import org.yordanoffnikolay.lmrproject.services.WorkdayService;

@RestController
@RequestMapping("/api/workday")
public class WorkdayRestController {

    private final AuthenticationHelper authenticationHelper;
    private final WorkdayService workdayService;

    public WorkdayRestController(AuthenticationHelper authenticationHelper, WorkdayService workdayService) {
        this.authenticationHelper = authenticationHelper;
        this.workdayService = workdayService;
    }

    @PostMapping
    public Workday createWorkday(@RequestBody DateDto dateDto) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String date = dateDto.getYear() + "-" + dateDto.getMonth() + "-" + dateDto.getDay();
        return workdayService.createWorkday(date);
    }
}
