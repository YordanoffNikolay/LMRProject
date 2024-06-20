package org.yordanoffnikolay.lmrproject.helpers;

import org.springframework.stereotype.Component;
import org.yordanoffnikolay.lmrproject.models.User;

@Component
public class AuthorizationHelper {

    public boolean isAdminOrManager(User user) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN") || authority.getAuthority().equals("MANAGER"));
    }
}
