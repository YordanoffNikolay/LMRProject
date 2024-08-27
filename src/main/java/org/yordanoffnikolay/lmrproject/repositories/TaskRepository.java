package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yordanoffnikolay.lmrproject.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
