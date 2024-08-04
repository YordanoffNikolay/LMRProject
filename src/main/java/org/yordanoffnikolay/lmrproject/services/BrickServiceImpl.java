package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.models.Brick;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.BrickRepository;

import java.util.*;

import static org.yordanoffnikolay.lmrproject.services.UserServiceImpl.UNAUTHORIZED;

@Service
public class BrickServiceImpl implements BrickService {

    private final BrickRepository brickRepository;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public BrickServiceImpl(BrickRepository brickRepository, AuthenticationHelper authenticationHelper) {
        this.brickRepository = brickRepository;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void createBrick(BrickDto brickDto) {
        checkAuthorities();
        if (brickRepository.findByName(brickDto.getName()).isPresent()) {
            throw new DuplicateEntityException("Brick", "name", brickDto.getName());
        }
        try {
            Brick brick = new Brick();
            brick.setName(brickDto.getName());
            brick.setUsers(new HashSet<>());
            brick.setWorkplaces(new HashSet<>());
            brickRepository.save(brick);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public void deleteBrick(long id, User loggedUser) {
        checkAuthorities();
        Brick brickToDelete = getBrick(id);
        brickRepository.delete(brickToDelete);
        System.out.println(brickRepository.findAll());
    }

    private Brick getBrick(long id) {
        if (brickRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Brick", id);
        }
        return brickRepository.findById(id).get();
    }

    @Override
    public void updateBrick(long id, BrickDto brickDto, User loggedUser) {
        checkAuthorities();
        Brick brickToUpdate = getBrick(id);
        brickToUpdate.setName(brickDto.getName());
        brickRepository.save(brickToUpdate);
    }

    private static void checkAuthorities() {
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
    }

    @Override
    public List<Brick> getBricks() {
        return brickRepository.findAll();
    }

    @Override
    public Optional<Brick> getBrickByName(String name) {
        return brickRepository.findByName(name);
    }
}
