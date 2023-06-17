package com.cyphase.eventmanagement.entity;

import com.cyphase.eventmanagement.utils.Constant;
import jakarta.persistence.*;

@Entity(name = "Contact")
@Table(name = "contact")
public class Contact {

    public Contact(int type, String value, Person person) {
        this.type = type;
        this.value = value;
        this.person = person;
    }

    @Transient
    private static final String[] TYPE_VALUES = {
            "Mobile",
            "Email",
            "Address"
    };

    @GeneratedValue(
            generator = Constant.GLOBAL_SEQUENCE_NAME,
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private int type = 0;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String[] getTypeValues() {
        return TYPE_VALUES;
    }
}
