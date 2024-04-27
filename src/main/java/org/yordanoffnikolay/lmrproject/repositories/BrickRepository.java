package org.yordanoffnikolay.lmrproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yordanoffnikolay.lmrproject.models.Brick;

@Repository
public interface BrickRepository extends JpaRepository<Brick, Long> {
}
