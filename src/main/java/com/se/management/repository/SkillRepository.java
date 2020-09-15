package com.se.management.repository;

import com.se.management.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

   // List<Skill> findTop3BySearcherIdOrderByScoreDesc(long searcherId);

//     List<Skill> findBySearcherId(long searcherId);

  //  List<Class> findAllBySkillName(String skillName);
}
