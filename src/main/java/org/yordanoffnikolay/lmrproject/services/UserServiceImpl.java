package org.yordanoffnikolay.lmrproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yordanoffnikolay.lmrproject.exceptions.DuplicateEntityException;
import org.yordanoffnikolay.lmrproject.exceptions.EntityNotFoundException;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User createUser(User user) {
        boolean exists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (exists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        return userRepository.save(user);
    }
}
