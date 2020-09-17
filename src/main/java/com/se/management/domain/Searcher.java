package com.se.management.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.se.management.domain.base.Auditable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Searcher extends Auditable<String> implements Serializable {
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

    // TODO: commented for testing
//    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "searcher"
            // ,cascade = CascadeType.ALL
            ,cascade= { CascadeType.MERGE, CascadeType.PERSIST }
            ,fetch = FetchType.LAZY
            // ,orphanRemoval = true
    )
    private Set<SkillsScore> skillsScores = new HashSet<>();

    @Override
    public String toString() {
        return "Searcher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void addChild(SkillsScore comment) {
     //   skillsScores.add(comment);
        comment.setSearcher(this);
    }

    public void removeChild(SkillsScore comment) {
        skillsScores.remove(comment);
        comment.setSearcher(null);
    }

    public void removeCourses() {
        for (SkillsScore course : new HashSet<>(skillsScores)) {
            removeChild(course);
        }
    }

//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "searcher_addresses", joinColumns = @JoinColumn(name = "searcher_id"))
//    @AttributeOverrides({
//            @AttributeOverride(name = "addressLine1", column = @Column(name = "house_number")),
//            @AttributeOverride(name = "addressLine2", column = @Column(name = "street"))
//    })
//    private Set<Address> addresses = new HashSet<>();
//
//
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "searcher_skills_score",
//            joinColumns = { @JoinColumn(name = "searcher_id") },
//            inverseJoinColumns = { @JoinColumn(name = "skills_score_id") })
//    private Set<SkillsScore> skillsScores = new HashSet<>();
//
//
//
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JoinTable(name = "searcher_contact",
//            joinColumns = { @JoinColumn(name = "searcher_id") },
//            inverseJoinColumns = { @JoinColumn(name = "contact_id") })
//    private Set<Contact> contacts = new HashSet<>();
//
//
//
//    public Set<SkillsScore> addSkill(SkillsScore skillsScore) {
//        skillsScores.add(skillsScore);
//        return this.skillsScores;
//    }
//
//    public void removeSkill(SkillsScore skillsScore) {
//        skillsScores.remove(skillsScore);
//        skillsScore.setSearchers(null);
//    }
//
//    public Set<Address> addAddress(Address address) {
//        addresses.add(address);
//        return this.addresses;
//    }
}