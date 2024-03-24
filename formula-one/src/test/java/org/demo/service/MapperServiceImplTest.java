package org.demo.service;

import org.demo.model.EntryFeeStatus;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MapperServiceImplTest {
    @InjectMocks
    MapperServiceImpl mapperService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mapperService = new MapperServiceImpl();
    }

    @Test
    public void toEntityReturnsDomain() {
        // given
        FormulaOneItem formulaOneItem = FormulaOneItem.builder()
                .id(UUID.fromString("4ce77288-7e6a-4ece-b562-98061e925dc1"))
                .name("Williams")
                .foundationYear("1977")
                .championships(9).entryFeeStatus(EntryFeeStatus.NOT_PAID)
                .build();
        // when
        FormulaOneItemEntity formulaOneItemEntity = mapperService.toEntity(formulaOneItem);
        // then
        Assertions.assertEquals(formulaOneItem.getId(), formulaOneItemEntity.getId());
        Assertions.assertEquals(formulaOneItem.getName(), formulaOneItemEntity.getName());
        Assertions.assertEquals(formulaOneItem.getFoundationYear(), formulaOneItemEntity.getFoundationYear());
        Assertions.assertEquals(formulaOneItem.getChampionships(), formulaOneItemEntity.getChampionships());
        Assertions.assertEquals(formulaOneItem.getEntryFeeStatus(), formulaOneItemEntity.getEntryFeeStatus());
    }

    @Test
    public void toDomainReturnsEntity() {
        // given
        FormulaOneItemEntity formulaOneItemEntity =  new FormulaOneItemEntity(UUID.fromString("e2af100b-6f3c-4dba-8f65-322ab5c35dea"), "Ferrari", "1950",
                16, EntryFeeStatus.PAID);
        // when
        FormulaOneItem formulaOneItem = mapperService.toDomain(formulaOneItemEntity);
        // then
        Assertions.assertEquals(formulaOneItem.getId(), formulaOneItemEntity.getId());
        Assertions.assertEquals(formulaOneItem.getName(), formulaOneItemEntity.getName());
        Assertions.assertEquals(formulaOneItem.getFoundationYear(), formulaOneItemEntity.getFoundationYear());
        Assertions.assertEquals(formulaOneItem.getChampionships(), formulaOneItemEntity.getChampionships());
        Assertions.assertEquals(formulaOneItem.getEntryFeeStatus(), formulaOneItemEntity.getEntryFeeStatus());
    }
}
