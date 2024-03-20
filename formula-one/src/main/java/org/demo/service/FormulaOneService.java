package org.demo.service;

import org.demo.model.FormulaOneItem;

import java.util.List;

public interface FormulaOneService {
    List<FormulaOneItem> getTeams();

    FormulaOneItem addTeam(FormulaOneItem formulaOneItem);

    FormulaOneItem updateTeam(FormulaOneItem formulaOneItem);

    void deleteTeam(String id);
}
