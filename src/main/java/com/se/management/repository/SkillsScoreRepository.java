package com.se.management.repository;

import com.se.management.domain.SkillsScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsScoreRepository extends JpaRepository<SkillsScore, Long> {

   // List<Skill> findTop3BySearcherIdOrderByScoreDesc(long searcherId);

//     List<Skill> findBySearcherId(long searcherId);

  //  List<Class> findAllBySkillName(String skillName);
}
