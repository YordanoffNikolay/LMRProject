package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.models.Workday;

import java.util.List;

public interface WorkdayService {

    Workday createWorkday(String date);

    Workday getWorkdayById(Long id);

    List<Workday> getAllWorkdays();

    List<Workday> getAllWorkdaysByUser();

    void deleteWorkday(Long id);
}
