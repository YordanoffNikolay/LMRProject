package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.models.Workday;

import java.util.Date;

@Repository
public interface WorkdayRepository extends JpaRepository<Workday, Long> {
    Workday findByDateAndUser(String date, User user);
}
