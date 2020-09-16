package com.se.management.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "contact")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "messenger_type_id", nullable = true)
    private MessengerType messengerType;

    @NotNull
    private String messengerAddress;


    @ManyToMany
    @JoinTable(name="searcher_contacts",
            joinColumns=@JoinColumn(name="contact_id"),
            inverseJoinColumns=@JoinColumn(name="searcher_id"))
    private Set<Searcher> searchers = new HashSet<>();



    public void removeChildSearcher(Searcher s) {
        searchers.remove(s);
        s.setSkillsScores(null);
    }

    public Set<Searcher> addSearcher(Searcher searcher) {
        searchers.add(searcher);
        return this.searchers;
    }
}
