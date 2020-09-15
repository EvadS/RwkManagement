package com.se.management.domain;

import com.se.management.model.enums.SkillName;
import com.se.management.model.converters.SkillNameConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills_score")
@Builder(toBuilder = true)
public class SkillsScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = true)
    private SkillItem skill;

    @Min(0)
    @Max(10)
    private byte score;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "searcher_id", nullable = false)
//    private Searcher searcher;
    @ManyToMany
    @JoinTable(name="searcher_skills",
            joinColumns=@JoinColumn(name="skills_score_id"),
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
