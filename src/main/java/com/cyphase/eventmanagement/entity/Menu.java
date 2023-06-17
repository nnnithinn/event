package com.cyphase.eventmanagement.entity;

import com.cyphase.eventmanagement.utils.Constant;
import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {

    @GeneratedValue(
            generator = Constant.GLOBAL_SEQUENCE_NAME,
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    @Column(name = "menuname", nullable = false, unique = true)
    private String menuName;


    @Column(name = "classname", nullable = false, unique = true)
    private String fullyQualifiedClassName;

}
