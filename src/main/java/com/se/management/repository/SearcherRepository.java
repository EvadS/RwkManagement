package com.se.management.repository;

import com.se.management.domain.Searcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearcherRepository extends JpaRepository<Searcher, Long> {

}
