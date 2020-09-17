package com.se.management.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "messenger_type")
public class MessengerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;
//
//    @OneToMany(mappedBy = "messengerType"
//            ,fetch = FetchType.LAZY)
//    private Set<Contact> contacts = new HashSet<>();
}