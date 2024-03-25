package org.demo.service;

import org.demo.model.FormulaOneItem;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
class ValidationServiceImpl implements ValidationService {

    static final int START_DATE = 1949;
    static final int END_DATE = 2025;

    @Override
    public boolean foundationYearIsValid(FormulaOneItem formulaOneItem) {
        try {
            Year year = Year.of(Integer.parseInt(formulaOneItem.getFoundationYear()));
            return year.isAfter(Year.of(START_DATE)) && year.isBefore(Year.of(END_DATE));
        } catch (Exception e) {
            return false;
        }
    }
}
