package com.se.management.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "SkillsScore")
@Table(name = "skills_score")
@Builder(toBuilder = true)
public class SkillsScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Max(10)
    private byte score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "searcher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Searcher searcher;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;


    @Override
    public String toString() {
        return "SkillsScore{" +
                "id=" + id +
                ", score=" + score +
                ", skill='" + skill.getName() + '\'' +
                '}';
    }
}
