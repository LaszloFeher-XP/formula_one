package org.demo.service;

import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.springframework.stereotype.Component;

@Component
class MapperServiceImpl implements MapperService {
    @Override
    public FormulaOneItemEntity toEntity(FormulaOneItem formulaOneItem) {
        return new FormulaOneItemEntity(
                formulaOneItem.getId(),
                formulaOneItem.getName(),
                formulaOneItem.getFoundationYear(),
                formulaOneItem.getChampionships(),
                formulaOneItem.getEntryFeeStatus());
    }

    @Override
    public FormulaOneItem toDomain(FormulaOneItemEntity formulaOneItemEntity) {
        return FormulaOneItem.builder()
                .id(formulaOneItemEntity.getId())
                .name(formulaOneItemEntity.getName())
                .foundationYear(formulaOneItemEntity.getFoundationYear())
                .championships(formulaOneItemEntity.getChampionships())
                .entryFeeStatus(formulaOneItemEntity.getEntryFeeStatus())
                .build();
    }
}
