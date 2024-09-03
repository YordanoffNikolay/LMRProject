package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Workday;
import org.yordanoffnikolay.lmrproject.repositories.WorkdayRepository;

import java.util.List;

@Service
public class WorkdayServiceImpl implements WorkdayService {

    private final AuthenticationHelper authenticationHelper;
    private final WorkdayRepository workdayRepository;

    public WorkdayServiceImpl(AuthenticationHelper authenticationHelper, WorkdayRepository workdayRepository) {
        this.authenticationHelper = authenticationHelper;
        this.workdayRepository = workdayRepository;
    }

    @Override
    public Workday createWorkday(String date) {
        Workday workday = new Workday();
        workday.setDate(date);
        workday.setUser(authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication()));
        if (workdayRepository.findByDateAndUser(workday.getDate(), workday.getUser()) != null) {
            throw new RuntimeException("Workday already exists");
        }
        return workdayRepository.save(workday);
    }

    @Override
    public Workday getWorkdayById(Long id) {
        Workday workday = workdayRepository.findById(id).orElse(null);
        if (workday == null) {
            throw new RuntimeException("Workday not found");
        }
        return workday;
    }

    @Override
    public List<Workday> getAllWorkdays() {
        return workdayRepository.findAll();
    }

    /** Method for getting all workdays by LOGGED USER. Only for logged user **/
    @Override
    public List<Workday> getAllWorkdaysByUser() {
        return workdayRepository.findAllByUser(authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication()));
    }

    @Override
    public void deleteWorkday(Long id) {
        Workday workday = workdayRepository.findById(id).orElse(null);
        if (workday.getUser() != authenticationHelper.tryGetUser(SecurityContextHolder.getContext().getAuthentication())) {
            throw new AuthorizationException("You are not authorized ");
        }
        workdayRepository.deleteById(id);
    }
}
