package com.ergo.task.repository;

import com.ergo.task.domain.PersonDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDataRepository extends JpaRepository<PersonDataEntity, Long>,
        JpaSpecificationExecutor<PersonDataEntity> {

    Optional<PersonDataEntity> findByPersonalId(String id);
}
