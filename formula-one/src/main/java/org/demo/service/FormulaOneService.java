package org.demo.service;

import org.apache.coyote.BadRequestException;
import org.demo.model.DeleteResponse;
import org.demo.model.FormulaOneItem;

import java.util.List;

public interface FormulaOneService {
    List<FormulaOneItem> getTeams();

    FormulaOneItem addTeam(FormulaOneItem formulaOneItem) ;

    FormulaOneItem updateTeam(FormulaOneItem formulaOneItem);

    DeleteResponse deleteTeam(String id);
}
