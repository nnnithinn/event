package com.cyphase.eventmanagement.entity;

import com.cyphase.eventmanagement.utils.Constant;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Person")
@Table(name = "person")
public class Person {

    @Transient
    private static final String[] STATUS_VALUES = {
      "Active"
    };

    @GeneratedValue(
            generator = Constant.GLOBAL_SEQUENCE_NAME,
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Date dateOfBirth;

    private int status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private SystemUser systemUser;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> addresses) {
        this.contacts = addresses;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
