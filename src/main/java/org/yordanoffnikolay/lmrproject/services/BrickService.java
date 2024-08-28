package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.models.Brick;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface BrickService{

    void createBrick(BrickDto brickDto);

    void deleteBrick(long id, User loggedUser);

    List<Brick> getBricks();

    Brick getBrick(long id);

    void updateBrick(long id, BrickDto brickDto, User loggedUser);
}
