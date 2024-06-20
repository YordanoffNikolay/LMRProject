package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yordanoffnikolay.lmrproject.dtos.UpdateUserDto;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<User> getAll();
    User getById(Long id);
    User getByUsername(String username);
    User createUser(User user, User loggedUser);
    User updateUser(Long id, UpdateUserDto updateUserDto, User loggedUser);
    void deleteUser(User user, long id);
}
