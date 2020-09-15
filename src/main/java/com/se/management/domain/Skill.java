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
@Builder(toBuilder = true)
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(mappedBy = "skill_name", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)

    @NotNull
   // @Convert(converter = SkillNameConverter.class)
   // private SkillName skillName = SkillName.NOT_SET;

    private  String name;

    @Min(0)
    @Max(10)
    private byte score;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "searcher_id", nullable = false)
//    private Searcher searcher;
    @ManyToMany
    @JoinTable(name="searcher_skills",
            joinColumns=@JoinColumn(name="skill_id"),
            inverseJoinColumns=@JoinColumn(name="searcher_id"))

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
//            mappedBy = "skills")
    private Set<Searcher> searchers = new HashSet<>();

//    public void removeChild(Searcher s) {
//        skills.remove(s);
//        s.setTags(null);
//    }

    public Set<Searcher> addSearcher(Searcher searcher) {
        searchers.add(searcher);
        return this.searchers;
    }
}
