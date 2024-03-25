package org.demo.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.error.InvalidTeamException;
import org.demo.error.InvalidRequestException;
import org.demo.model.DeleteResponse;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.demo.persistence.repository.FormulaOneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class FormulaOneServiceImpl implements FormulaOneService {

    FormulaOneRepository repository;

    MapperService mapperService;

    ValidationService validationService;

    @Override
    public List<FormulaOneItem> getTeams() {
        return repository.findAll()
                .stream().map(mapperService::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public FormulaOneItem addTeam(FormulaOneItem formulaOneItem)  {
        if (formulaOneItem.getId() == null) {
            formulaOneItem.setId((UUID.randomUUID()));
        }
        if (repository.existsByNameIgnoreCaseIn(formulaOneItem.getName()).orElse(false)) {
            throw new InvalidTeamException("The Team is in the list");
        }
        formulaItemIsValid(formulaOneItem);
        repository.save(mapperService.toEntity(formulaOneItem));
        return formulaOneItem;
    }

    @Override
    public FormulaOneItem updateTeam(FormulaOneItem formulaOneItem) {
        this.notExistsById(String.valueOf(formulaOneItem.getId()));
        if (teamDoesNotMatch(formulaOneItem)) {
            throw new InvalidTeamException("The Team is in the list");
        }
        formulaItemIsValid(formulaOneItem);
        repository.save(mapperService.toEntity(formulaOneItem));
        return formulaOneItem;
    }

    @Override
    public DeleteResponse deleteTeam(String id) {
        this.notExistsById(id);
        repository.deleteById(UUID.fromString(id));
        return DeleteResponse.builder().message(String.format("Team deleted with ID: %s", id)).build();
    }

    private void notExistsById(String id) {
        if (!repository.existsById(UUID.fromString(id))) {
            throw new InvalidRequestException(String.format("Team cannot be found with this ID: %s", id));
        }
    }

    private boolean teamDoesNotMatch(FormulaOneItem formulaOneItem) {
        List<FormulaOneItemEntity> formulaOneItems = repository.findByNameIgnoreCaseIn(formulaOneItem.getName()).orElse(List.of());
        return formulaOneItems.stream().anyMatch(item -> !item.getId().equals(formulaOneItem.getId()));
    }

    private void formulaItemIsValid(FormulaOneItem formulaOneItem) {
        if(!validationService.foundationYearIsValid(formulaOneItem)){
            throw new InvalidRequestException("Inappropriate Date format.");
        }
    }
}
