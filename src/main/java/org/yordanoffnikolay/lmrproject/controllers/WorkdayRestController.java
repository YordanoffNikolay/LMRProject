package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.DateDto;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Workday;
import org.yordanoffnikolay.lmrproject.services.WorkdayService;

import java.util.List;

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

    @GetMapping
    public List<Workday> getAllWorkdays() {
        try {
            authenticationHelper.isAuthenticated();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        //todo
        return workdayService.getAllWorkdays();
    }

    @GetMapping("/{id}")
    public Workday getWorkday(@PathVariable Long id) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return workdayService.getWorkdayById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkday(@PathVariable Long id) {
        try {
            authenticationHelper.isAuthenticated();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        workdayService.deleteWorkday(id);
    }

    //todo do i need to implement this?
//
//    @PutMapping("/{id}")
//    public Workday updateWorkday(@PathVariable Long id, @RequestBody DateDto dateDto) {
//        try {
//            authenticationHelper.isAuthenticated();
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//        String date = dateDto.getYear() + "-" + dateDto.getMonth() + "-" + dateDto.getDay();
////        return workdayService.updateWorkday(id, date);
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
//    }
}
