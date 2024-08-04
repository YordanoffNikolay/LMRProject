package org.yordanoffnikolay.lmrproject.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.yordanoffnikolay.lmrproject.exceptions.AuthorizationException;
import org.yordanoffnikolay.lmrproject.models.User;
import org.yordanoffnikolay.lmrproject.services.UserService;

@Component
public class AuthenticationHelper {

    private static final String AUTH_ERR = "Invalid Authentication";
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

//    public User tryGetUser(Authentication authentication) {
//        String username;
//        try {
//            username = authentication.getName();
//        } catch (NullPointerException e) {
//            throw new AuthorizationException(AUTH_ERR);
//        }
//        return userService.getByUsername(username);
//    }

    public User tryGetUser(Authentication authentication) throws AuthorizationException {
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken || authentication.getDetails() == null) {
            throw new AuthorizationException(AUTH_ERR);
        }
        return userService.getByUsername(authentication.getName());
    }

    public void isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return;
        }
        authentication.isAuthenticated();
    }
}
