package org.yordanoffnikolay.lmrproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.UserDto;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.mappers.UserMapper;
import org.yordanoffnikolay.lmrproject.models.AuthRequest;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.services.JwtService;
import org.yordanoffnikolay.lmrproject.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        try {
            return userService.getById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping()
    public User create(@RequestBody UserDto userDto , Authentication authentication) {
        try {
            User user = userMapper.fromDto(userDto);
            return userService.createUser(user, authentication);
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable long id) {
        try {
            UserDetails loggedUser = authenticationHelper.tryGetUser(authentication);
            userService.deleteUser(authentication, id, loggedUser);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request!");
        }
    }
}
