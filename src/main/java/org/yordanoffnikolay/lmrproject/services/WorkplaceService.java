package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.dtos.WorkplaceDto;
import org.yordanoffnikolay.lmrproject.models.Workplace;

import java.util.List;

public interface WorkplaceService {
   List<Workplace> getAll();

   Workplace getById(Long id);

   Workplace create(Workplace workplace);

   void delete(Long id);

   Workplace update(Long id, WorkplaceDto workplaceDto);

    void addClientToWorkplace(Long id, ClientDto clientDto);
}
