package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.models.Workplace;
import org.yordanoffnikolay.lmrproject.repositories.WorkplaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public WorkplaceServiceImpl(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    public Optional<Workplace> getById(Long id) {
        return workplaceRepository.findById(id);
    }

    @Override
    public List<Workplace> getAll() {
        return workplaceRepository.findAll();
    }
}
