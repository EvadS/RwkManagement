package com.se.management.repository;

import com.se.management.domain.SkillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface SkillItemRepository extends JpaRepository<SkillItem, Long>, JpaSpecificationExecutor<SkillItem> {

    Optional<SkillItem> findTop1ByName(String name);
}