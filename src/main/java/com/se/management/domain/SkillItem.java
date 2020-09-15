package com.se.management.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skill_item")
@Data
public class SkillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "skill"
            // ,cascade = CascadeType.ALL
            ,fetch = FetchType.LAZY
            // ,orphanRemoval = true
    )
    private Set<SkillsScore> skillsScores = new HashSet<SkillsScore>();

}

