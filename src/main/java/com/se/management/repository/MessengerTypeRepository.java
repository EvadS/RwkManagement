package com.se.management.repository;

import com.se.management.domain.MessengerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessengerTypeRepository extends JpaRepository<MessengerType, Long>, JpaSpecificationExecutor<MessengerType> {

    Optional<MessengerType> findTop1ByName(String name);
}


