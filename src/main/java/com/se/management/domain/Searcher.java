package com.se.management.domain;

import com.se.management.domain.base.Auditable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"addresses"})
@Builder(toBuilder = true)
public class Searcher extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    //TODO: not set now
    private Date reviewDate;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String email;


    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "searcher_addresses", joinColumns = @JoinColumn(name = "searcher_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
    })
    private Set<Address> addresses = new HashSet<>();//

    @Override
    public String toString() {
        return "Searcher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}