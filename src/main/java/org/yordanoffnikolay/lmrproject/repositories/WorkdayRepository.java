package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.models.Workday;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkdayRepository extends JpaRepository<Workday, Long> {
    Workday findByDateAndUser(String date, User user);
    Workday findWorkdayByWorkdayId(long workdayId);
    List<Workday> findAllByUser(User user);
}
