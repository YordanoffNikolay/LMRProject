package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.models.Visit;
import org.yordanoffnikolay.lmrproject.repositories.VisitRepository;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit getVisitById(long id) {
        return visitRepository.getVisitByVisitId(id);
    }
}
