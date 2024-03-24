package org.demo.persistence.repository;

import org.demo.persistence.entity.FormulaOneItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormulaOneRepository extends JpaRepository<FormulaOneItemEntity, UUID> {

    @Query(nativeQuery= true, value="SELECT COUNT(*)>0 FROM formula f WHERE lower(f.name) LIKE lower(:name)")
    Optional<Boolean> existsByNameIgnoreCaseIn(@Param("name") String name);

    @Query(nativeQuery= true, value="SELECT * FROM formula f WHERE lower(f.name) LIKE lower(:name)")
    Optional<List<FormulaOneItemEntity>> findByNameIgnoreCaseIn(@Param("name") String name);
}
