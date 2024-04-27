package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yordanoffnikolay.lmrproject.models.Workplace;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {
}
