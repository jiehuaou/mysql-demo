package com.example.demo.repository;

import com.example.demo.entity.DepEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepStore extends CrudRepository<DepEntity, Long> {

    // find with empList in 1 single SQL
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"empList"})
    Optional<DepEntity> findWithChildById(Long id);

}
