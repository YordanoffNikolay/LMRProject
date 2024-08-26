package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.UpdateUserDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthorizationHelper;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final String UNAUTHORIZED = "You are not authorized to perform this action";

    private final UserRepository userRepository;
    private final AuthorizationHelper authorizationHelper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorizationHelper authorizationHelper) {
        this.userRepository = userRepository;
        this.authorizationHelper = authorizationHelper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            var userObj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .authorities(getRoles(userObj))
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }

    private String getRoles(User userObj) {
        Collection<? extends GrantedAuthority> roles = userObj.getAuthorities();
        StringBuilder rolesBuilder = new StringBuilder();
        for (GrantedAuthority role : roles) {
            rolesBuilder.append(role.getAuthority()).append(",");
        }
        return rolesBuilder.toString();
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", id);
        } else return user.get();
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User","username" ,username);
        } else return user.get();
    }

    @Override
    public User createUser(User userToBeCreated, User loggedUser) {
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!authorities.contains("ADMIN") && !authorities.contains("MANAGER")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        if (userRepository.findByUsername(userToBeCreated.getUsername()).isPresent()) {
            throw new DuplicateEntityException("User", "username", userToBeCreated.getUsername());
        }
        System.out.println("Breakpoint just before save");
        return userRepository.save(userToBeCreated);
    }

    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto, User loggedUser) {
        try{
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
            if(userToUpdate.getAuthorities().contains("ADMIN")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't update ADMIN user");
            }
            if (userToUpdate.getAuthorities().contains("MANAGER") && !authorizationHelper.isAdminOrManager(loggedUser)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
            }
            if (!userToUpdate.getUsername().equals(loggedUser.getUsername()) && !authorizationHelper.isAdminOrManager(loggedUser)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
            }
            userToUpdate.setPassword(updateUserDto.getPassword());
            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
    }

    @Override
    public void deleteUser(User user, long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        if (userToDelete.getAuthorities().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't delete ADMIN user");
        }
        if (!authorizationHelper.isAdminOrManager(user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        userRepository.deleteById(id);
    }
}
