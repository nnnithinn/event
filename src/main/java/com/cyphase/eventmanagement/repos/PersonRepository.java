package com.cyphase.eventmanagement.repos;

import com.cyphase.eventmanagement.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
