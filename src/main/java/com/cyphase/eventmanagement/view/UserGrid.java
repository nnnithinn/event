package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.entity.Person;
import com.cyphase.eventmanagement.entity.SystemUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.sql.Date;
import java.util.Arrays;

@Route(value = "users", layout = HomeView.class)
@RolesAllowed("ADMIN")
public class UserGrid extends Grid<Person> {

    public UserGrid() {
        super(Person.class);
        Column nameColumn = addColumn("Name");
        Column dateOfBirthColumn = addColumn("DateOfBirth");
        Column userNameColumn = addColumn("UserName");

        prependHeaderRow().join(nameColumn, dateOfBirthColumn, userNameColumn).setComponent(new Button(""));
    }

    public String getName(Person person) {
        return person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName();
    }

    public String getDateOfBirth(Person person) {
        Date dateOfBirth = person.getDateOfBirth();
        if (dateOfBirth == null) {
            return "";
        }
        return dateOfBirth.toString();
    }

    public String getUserName(Person person) {
        SystemUser systemUser = person.getSystemUser();
        return systemUser == null ? "No User Name Assigned" : systemUser.getUsername();
    }
}
