package com.cyphase.eventmanagement.entity;

import com.cyphase.eventmanagement.utils.Constant;
import com.cyphase.eventmanagement.utils.Encrypt;
import jakarta.persistence.*;

@Entity(name = "SystemUser")
@Table(name = "systemuser")
public class SystemUser {

    @Transient
    private static final String[] ROLE_VALUES = {
            "Admin",
            "User"
    };

    @GeneratedValue(
            generator = Constant.GLOBAL_SEQUENCE_NAME,
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;


    @OneToOne(mappedBy = "systemUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Person person;

    private String username;

    private int role = 1;

    @Convert(converter = Encrypt.class)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public static String[] getRoleValues() {
        return ROLE_VALUES;
    }
}
