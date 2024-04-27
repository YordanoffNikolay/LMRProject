package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    List<User> getAll();
    User getByUsername(String username);
    Optional<User> getById(Long id);
    User createUser(User user);

}
