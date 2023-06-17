package com.cyphase.eventmanagement.repos;

import com.cyphase.eventmanagement.entity.SystemUser;
import org.springframework.data.repository.CrudRepository;

public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {

    SystemUser findByUsername(String username);
}
