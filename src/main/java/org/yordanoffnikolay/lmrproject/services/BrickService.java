package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.models.Brick;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface BrickService{

    void createBrick(BrickDto brickDto, User loggedUser);

    void deleteBrick(BrickDto brickDto, User loggedUser);

    List<Brick> getBricks(User loggedUser);

    Optional<Brick> getBrickByName(String name);
}
