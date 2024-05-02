package org.yordanoffnikolay.lmrproject.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.yordanoffnikolay.lmrproject.dtos.UpdateUserDto;
import org.yordanoffnikolay.lmrproject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    Optional<User> getById(Long id);
    User createUser(User user, Authentication authentication);
    User deleteUser(Authentication authentication, @PathVariable Long id, UserDetails userDetails);
    User updateUser(Authentication authentication, @PathVariable Long id, @RequestBody UpdateUserDto updateUserDto);
}
