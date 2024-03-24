package org.demo.service;

import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;

public interface MapperService {
    FormulaOneItemEntity toEntity(FormulaOneItem formulaOneItem);
    FormulaOneItem toDomain(FormulaOneItemEntity formulaOneItemEntity);
}
