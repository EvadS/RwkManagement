package com.se.management.domain;


import com.se.management.domain.base.Auditable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class User extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String firstName;

    @NotNull
    @Size(max = 100)
    private String lastName;

    private Date reviewDate;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_addresses", joinColumns = @JoinColumn(name = "user_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
    })
    private Set<Address> addresses = new HashSet<>();

//

    public User() {

    }



    // Getters and Setters (Omitted for brevity)
}