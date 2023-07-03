package com.cyphase.eventmanagement.utils;

import com.cyphase.eventmanagement.entity.SystemUser;
import com.cyphase.eventmanagement.repos.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SystemUserRepository repo;

    public void processOAuthPostLogin(String username) {
        SystemUser existUser = repo.findByUsername(username);

        if (existUser == null) {
            SystemUser newUser = new SystemUser();
            newUser.setUsername(username);

            repo.save(newUser);

            System.out.println("Created new user: " + username);
        }

    }

}

