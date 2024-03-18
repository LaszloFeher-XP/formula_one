package org.demo.persistence.repository;

import org.demo.persistence.entity.FormulaOneItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FormulaOneRepository extends JpaRepository<FormulaOneItemEntity, UUID> {
}
