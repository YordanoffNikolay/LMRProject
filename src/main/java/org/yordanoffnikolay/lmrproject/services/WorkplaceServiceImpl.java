package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.ClientDto;
import org.yordanoffnikolay.lmrproject.dtos.WorkplaceDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.models.Client;
import org.yordanoffnikolay.lmrproject.models.Workplace;
import org.yordanoffnikolay.lmrproject.repositories.ClientRepository;
import org.yordanoffnikolay.lmrproject.repositories.WorkplaceRepository;

import java.util.List;

import static org.yordanoffnikolay.lmrproject.services.UserServiceImpl.UNAUTHORIZED;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;
    private final BrickService brickService;
    private final ClientRepository clientRepository;

    @Autowired
    public WorkplaceServiceImpl(WorkplaceRepository workplaceRepository, BrickService brickService, ClientRepository clientRepository) {
        this.workplaceRepository = workplaceRepository;
        this.brickService = brickService;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Workplace> getAll() {
        return workplaceRepository.findAll();
    }

    @Override
    public Workplace getById(Long id) {
        if (workplaceRepository.findById(id).isPresent()) {
            return workplaceRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Workplace", id);
        }
    }

    @Override
    public Workplace create(Workplace workplace) {
        getLoggedUserAuthorities();
        if (workplaceRepository.findByName(workplace.getName()).isPresent()) {
            throw new DuplicateEntityException("Workplace", "name", workplace.getName());
        }
        return workplaceRepository.save(workplace);
    }

    @Override
    public void delete(Long id) {
        getLoggedUserAuthorities();
        if (workplaceRepository.findById(id).isPresent()) {
            workplaceRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Workplace", id);
        }
    }

    @Override
    public Workplace update(Long id, WorkplaceDto workplaceDto) {
        Workplace workplace = getById(id);
        workplace.setName(workplaceDto.getName());
        workplace.setAddress(workplaceDto.getAddress());
//        if (workplaceDto.getBrickName() != null) {
//            workplace.setBrick(brickService.getBrickByName(workplaceDto.getBrickName()).get());
//        }
        getLoggedUserAuthorities();
        if (workplaceRepository.findById(workplace.getId()).isPresent()) {
            return workplaceRepository.save(workplace);
        } else {
            throw new EntityNotFoundException("Workplace", workplace.getId());
        }
    }

    private void getLoggedUserAuthorities() {
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
    }

    @Override
    public void addClientToWorkplace(Long id, ClientDto clientDto) {
        Workplace workplace = workplaceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workplace", id));
        Client client = clientRepository.findByName(clientDto.getName())
                .orElseThrow(() -> new EntityNotFoundException("Client", "name", clientDto.getName()));
        if (workplace.getClients().contains(client)) {
            throw new DuplicateEntityException("Client", "name", clientDto.getName());
        }
        List<Client> workplaceClients = workplace.getClients();
        workplaceClients.add(client);
        workplace.setClients(workplaceClients);
        workplaceRepository.save(workplace);
    }
}
