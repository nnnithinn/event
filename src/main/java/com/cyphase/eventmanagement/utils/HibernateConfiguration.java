package com.cyphase.eventmanagement.utils;

import com.cyphase.eventmanagement.entity.Contact;
import com.cyphase.eventmanagement.entity.Person;
import com.cyphase.eventmanagement.entity.SystemUser;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.stereotype.Component;


public class HibernateConfiguration {

    public static Configuration getConfig() {
        Configuration configuration = new Configuration();

        // Set database connection properties
        configuration.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        configuration.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mydatabase");
        configuration.setProperty(Environment.USER, "your_username");
        configuration.setProperty(Environment.PASS, "your_password");

        // Set Hibernate properties
        configuration.setProperty(Environment.DIALECT, PostgreSQLDialect.class.getName());
        configuration.setProperty(Environment.SHOW_SQL, "true");
        configuration.setProperty(Environment.FORMAT_SQL, "true");

        // Add entity classes
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(SystemUser.class);
        configuration.addAnnotatedClass(Contact.class);

        return configuration;
    }
}
