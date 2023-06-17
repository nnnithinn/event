package com.cyphase.eventmanagement.view;

import com.cyphase.eventmanagement.entity.Contact;
import com.cyphase.eventmanagement.entity.Person;
import com.cyphase.eventmanagement.entity.SystemUser;
import com.cyphase.eventmanagement.repos.SystemUserRepository;
import com.cyphase.eventmanagement.utils.Utils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "add-user", layout = HomeView.class)
@PermitAll
public class UserEditor extends FormLayout implements BeforeEnterObserver {

    @Autowired
    private SystemUserRepository systemUserRepository;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField middleNameField;
    private DatePicker dateOfBirthField;
    private TextField phoneNumberField;
    private TextField emailField;
    private TextField addressField;

    private TextField userNameField;
    private PasswordField passwordField;
    private PasswordField confirmPassword;
    private Select<String> roleSelectField;

    public UserEditor(SystemUserRepository systemUserRepository) {
        getElement().getStyle().set("padding", "30px");
        add(firstNameField = new TextField("First Name"));
        add(middleNameField = new TextField("Middle Name"));
        add(lastNameField = new TextField("Last Name"));
        add(dateOfBirthField = new DatePicker("Date of Birth"));
        add(phoneNumberField = new TextField("Mobile / Phone Number"));
        add(emailField = new TextField("Email"));
        add(addressField = new TextField("Address"));
        add(userNameField = new TextField("User Name"));
        add(passwordField = new PasswordField("Password"));
        add(confirmPassword = new PasswordField("Confirm Password"));
        add(roleSelectField = new Select<>());
        roleSelectField.setItems(SystemUser.getRoleValues());
        roleSelectField.setLabel("Role");
        Button submit = new Button("Submit", new Icon(VaadinIcon.CHECK), event -> saveUser());
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(submit);
        buttonLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(buttonLayout);
        setColspan(buttonLayout, 2);
    }

    private void saveUser() {
        String firstName = firstNameField.getValue();
        String middleName = middleNameField.getValue();
        String lastName = lastNameField.getValue();
        String mobile = phoneNumberField.getValue();
        String email = emailField.getValue();
        String address = addressField.getValue();

        Person person = new Person();
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        List<Contact> contacts = new ArrayList<>();
        if (!Utils.isWhite(mobile)) {
            contacts.add(new Contact(0, mobile, person));
        }
        if (!Utils.isWhite(email)) {
            contacts.add(new Contact(1, email, person));
            contacts.add(new Contact(2, address, person));
        }

        person.setContacts(contacts);

        SystemUser systemUser = new SystemUser();
        systemUser.setPerson(person);
        systemUser.setUsername(userNameField.getValue());
        systemUser.setPassword(passwordField.getValue());
        systemUser.setRole(1);
        person.setSystemUser(systemUser);
        systemUserRepository.save(systemUser);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Hide the route from the URL
        event.forwardTo("add-user");
    }
}
