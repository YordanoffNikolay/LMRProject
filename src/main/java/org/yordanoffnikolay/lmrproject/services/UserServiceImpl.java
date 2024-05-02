package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.yordanoffnikolay.lmrproject.dtos.UpdateUserDto;
import org.yordanoffnikolay.lmrproject.dtos.UserDto;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.helpers.AuthenticationHelper;
import org.yordanoffnikolay.lmrproject.mappers.UserMapper;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.models.Visit;
import org.yordanoffnikolay.lmrproject.repositories.UserRepository;
import org.yordanoffnikolay.lmrproject.repositories.VisitRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final String UNAUTHORIZED = "You are not authorized to perform this action";

    private final UserRepository userRepository;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    private final VisitRepository visitRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationHelper authenticationHelper, UserMapper userMapper, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
        this.visitRepository = visitRepository;
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
    public Optional<User> getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", id);
        } else return user;
    }

    @Override
    public User createUser(User user, Authentication authentication) {
        boolean exists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (!authenticationHelper.isAdmin(authentication) && !authenticationHelper.isManager(authentication)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        if (exists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        return userRepository.save(user);
    }

    public User updateUser(Authentication authentication, @PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        try{
            UserDetails loggedUser = authenticationHelper.tryGetUser(authentication);
            User userToUpdate = userMapper.fromDto(id, updateUserDto);
            if(userToUpdate.getAuthorities().contains("ADMIN")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't update ADMIN user");
            }
            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }
        //todo: continue from here updateUser
    }

    public User deleteUser(Authentication authentication, @PathVariable Long id, UserDetails loggedUser) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        if (userToDelete.getAuthorities().contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't delete ADMIN user");
        }
        if (!authenticationHelper.isAdmin(authentication) && !authenticationHelper.isManager(authentication)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED);
        }

        //todo: continue from here
        return userToDelete;
    }
}
