package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    Optional<User> getById(Long id);
    User createUser(User user);

}
