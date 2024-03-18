package org.demo.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.demo.persistence.repository.FormulaOneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormulaOneServiceImpl implements FormulaOneService{

    FormulaOneRepository repository;

    @Override
    public FormulaOneItem addTeam(FormulaOneItem formulaOneItem) {
        if (formulaOneItem.getId() == null) {
            formulaOneItem.setId((UUID.randomUUID()));
        }
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity();
        BeanUtils.copyProperties(formulaOneItem, formulaOneItemEntity);
        repository.save(formulaOneItemEntity);
        return formulaOneItem;
    }
}
