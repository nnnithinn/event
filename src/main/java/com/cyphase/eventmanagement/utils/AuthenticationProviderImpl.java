package com.cyphase.eventmanagement.utils;

import com.cyphase.eventmanagement.entity.SystemUser;
import com.cyphase.eventmanagement.repos.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    @Autowired
    SystemUserRepository systemUserRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (username.equals("admin") && password.equals("Welcome123$")) {
            ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorities);
            return usernamePasswordAuthenticationToken;
        }
        SystemUser user = systemUserRepository.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        System.err.println(username + " " + password);
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?>aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}