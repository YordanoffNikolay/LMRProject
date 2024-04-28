package org.yordanoffnikolay.lmrproject.mappers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.yordanoffnikolay.lmrproject.dtos.UpdateUserDto;
import org.yordanoffnikolay.lmrproject.dtos.UserDto;
import org.yordanoffnikolay.lmrproject.models.Role;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.repositories.RoleRepository;
import org.yordanoffnikolay.lmrproject.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserMapper(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User fromDto(long id, UpdateUserDto updateUserDto) {
        User existingUser = userService.getById(id).orElseThrow(EntityNotFoundException::new);
        existingUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        return existingUser;
    }


    public User fromDto(UserDto userDto) {

        String password = passwordEncoder.encode(userDto.getPassword());
        Role userRole = roleRepository.findByAuthority("USER");
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(userRole);

        return new User(
                userDto.getUsername(),
                password,
                rolesSet
        );
    }
}
