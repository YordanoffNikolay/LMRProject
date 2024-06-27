package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.models.Workplace;

import java.util.List;
import java.util.Optional;

public interface WorkplaceService {

    List<Workplace> getAll();

    Optional<Workplace> getById(Long id);
}
