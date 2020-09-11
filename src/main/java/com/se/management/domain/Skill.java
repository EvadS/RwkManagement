package com.se.management.domain;

import com.se.management.SkillName;
import com.se.management.converters.SkillNameConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(mappedBy = "skill_name", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)

    @NotNull
    @Convert(converter = SkillNameConverter.class)
    private SkillName skillName;

    @Min(0)
    @Max(10)
    private String scores;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Skill() {
    }


}
