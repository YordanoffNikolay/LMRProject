package org.yordanoffnikolay.lmrproject.services;

import org.yordanoffnikolay.lmrproject.dtos.BrickDto;
import org.yordanoffnikolay.lmrproject.models.User;

public interface BrickService{

    void createBrick(BrickDto brickDto, User loggedUser);
}
