package com.example.demo.repository;

import com.example.demo.entity.AbstractEmpAgg;
import com.example.demo.entity.EmpEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpStore extends CrudRepository<EmpEntity, Long> {

    // modify to use custom sql
    @Modifying(clearAutomatically = true)
    @Query("update EmpEntity t set t.data=t.data + :inc where t.id= :empId")
    int changeData(@Param("empId") Long id, @Param("inc") Double inc);

    // load the dep property eagerly, even through it is lazy
    // use left outer join in 1 sql
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"dep"})
    Optional<EmpEntity> findEntireById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"dep"})
    List<EmpEntity> findSpecial(Long depId);

    @Query("select t.depId as dept, count(1) as empTotal from EmpEntity t group by t.depId")
    List<AbstractEmpAgg> totalPerDept();
}
