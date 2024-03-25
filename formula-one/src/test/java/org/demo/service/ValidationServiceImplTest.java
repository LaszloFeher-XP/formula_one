package org.demo.service;

import org.demo.model.EntryFeeStatus;
import org.demo.model.FormulaOneItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceImplTest {

    @InjectMocks
    ValidationServiceImpl validationService;

    FormulaOneItem formulaOneItem;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        validationService = new ValidationServiceImpl();
        formulaOneItem = FormulaOneItem.builder()
                .id(UUID.fromString("4ce77288-7e6a-4ece-b562-98061e925dc1"))
                .name("Williams")
                .foundationYear("2010")
                .championships(9).entryFeeStatus(EntryFeeStatus.NOT_PAID)
                .build();
    }

    @Test
    public void correctFormatReturnsTrue(){
        // when
        // then
        Assertions.assertTrue(validationService.foundationYearIsValid(formulaOneItem));
    }

    @Test
    public void correctLowFormatReturnsTrue(){
        // when
        formulaOneItem.setFoundationYear("1950");
        // then
        Assertions.assertTrue(validationService.foundationYearIsValid(formulaOneItem));
    }

    @Test
    public void correctHighFormatReturnsTrue(){
        // when
        formulaOneItem.setFoundationYear("2024");
        // then
        Assertions.assertTrue(validationService.foundationYearIsValid(formulaOneItem));
    }

    @Test
    public void stringFormatReturnsFalse(){
        // given
        formulaOneItem.setFoundationYear("test");
        // when
        // then
        Assertions.assertFalse(validationService.foundationYearIsValid(formulaOneItem));
    }
    @Test
    public void oldDateReturnsFalse(){
        // given
        formulaOneItem.setFoundationYear("1940");
        // when
        // then
        Assertions.assertFalse(validationService.foundationYearIsValid(formulaOneItem));
    }

    @Test
    public void futureDateReturnsFalse(){
        // given
        formulaOneItem.setFoundationYear("2030");
        // when
        // then
        Assertions.assertFalse(validationService.foundationYearIsValid(formulaOneItem));
    }
}
