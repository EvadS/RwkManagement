package com.se.management.repository;

import com.se.management.domain.Skill;
import com.se.management.domain.SkillsScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsScoreRepository extends JpaRepository<SkillsScore, Long> {

    List<SkillsScore> findTop3BySearcherIdOrderByScoreDesc(long searcherId);

//     List<Skill> findBySearcherId(long searcherId);

  //  List<Class> findAllBySkillName(String skillName);

    List<SkillsScore> findBySearcherId(long searcherId);

    void deleteByIdIn(List<Long> ids);
}
