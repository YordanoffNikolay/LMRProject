package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Workday;
import org.yordanoffnikolay.lmrproject.repositories.WorkdayRepository;

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
}
