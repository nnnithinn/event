package com.cyphase.eventmanagement.utils;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public Object getAuthenticatedUser() {
        Optional<Object> authenticatedUser = authenticationContext.getAuthenticatedUser(Object.class);
        return authenticatedUser.isPresent() ? authenticatedUser.get() : null;
    }

    public void logout() {
        authenticationContext.logout();
    }

    public boolean isLoggedIn() {
        return authenticationContext.isAuthenticated();
    }
}
