package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.models.Brick;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.BrickRepository;

import java.util.HashSet;
import java.util.List;

import static org.yordanoffnikolay.lmrproject.services.UserServiceImpl.UNAUTHORIZED;

@Service
public class BrickServiceImpl implements BrickService {

    private final BrickRepository brickRepository;

    @Autowired
    public BrickServiceImpl(BrickRepository brickRepository) {
        this.brickRepository = brickRepository;
    }

    @Override
    public void createBrick(BrickDto brickDto, User loggedUser) {
        List<String> authorities = loggedUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
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
    public void deleteBrick(BrickDto brickDto, User loggedUser) {
        List<String> authorities = loggedUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        if (brickRepository.findByName(brickDto.getName()).isEmpty()) {
            throw new EntityNotFoundException("Brick", "name", brickDto.getName());
        }
        Brick brickToDelete = brickRepository.findByName(brickDto.getName()).get();
        brickRepository.delete(brickToDelete);
        System.out.println(brickRepository.findAll());
    }
}
