package org.yordanoffnikolay.lmrproject.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.services.UserService;

@Component
public class AuthenticationHelper {

    private static final String AUTH_ERR = "Invalid Authentication";
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public UserDetails tryGetUser(Authentication authentication) {
        String username;
        try {
            username = authentication.getName();
        } catch (NullPointerException e) {
            throw new AuthorizationException(AUTH_ERR);
        }
        return userService.loadUserByUsername(username);
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public boolean isAdmin(Authentication authentication) {
        UserDetails user = tryGetUser(authentication);
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
    }

    public boolean isManager(Authentication authentication) {
        UserDetails user = tryGetUser(authentication);
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("MANAGER"));
    }
}
