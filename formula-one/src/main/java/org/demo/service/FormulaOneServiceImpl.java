package org.demo.service;

import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.error.InvalidTeamException;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.demo.persistence.repository.FormulaOneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FormulaOneServiceImpl implements FormulaOneService {

    FormulaOneRepository repository;

    @Override
    public List<FormulaOneItem> getTeams() {
        return repository.findAll()
                .stream().map(this::buildFormulaOneItem)
                .collect(Collectors.toList());
    }

    @Override
    public FormulaOneItem addTeam(FormulaOneItem formulaOneItem) {
        if (formulaOneItem.getId() == null) {
            formulaOneItem.setId((UUID.randomUUID()));
        }
        if (teamExisting(formulaOneItem)) {
            throw new InvalidTeamException("The Team is in the list");
        }
        repository.save(mapToFormulaOneItemEntity(formulaOneItem));
        return formulaOneItem;
    }

    @Override
    public FormulaOneItem updateTeam(FormulaOneItem formulaOneItem) {
        if (!repository.existsById(formulaOneItem.getId())) {
            throw new InvalidTeamException(String.format("Team cannot be found with this ID: %s", formulaOneItem.getId()));
        }
        FormulaOneItemEntity formulaOneItemEntity = repository.getReferenceById(formulaOneItem.getId());
        formulaOneItemEntity.setName(formulaOneItem.getName());
        formulaOneItemEntity.setFoundationYear(formulaOneItem.getFoundationYear());
        formulaOneItemEntity.setChampionships(formulaOneItem.getChampionships());
        formulaOneItemEntity.setEntryFeeStatus(formulaOneItem.getEntryFeeStatus());
        repository.save(formulaOneItemEntity);
        return formulaOneItem;
    }

    @Override
    public void deleteTeam(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    private FormulaOneItemEntity mapToFormulaOneItemEntity(FormulaOneItem formulaOneItem) {
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity();
        BeanUtils.copyProperties(formulaOneItem, formulaOneItemEntity);
        return formulaOneItemEntity;
    }

    private boolean teamExisting(FormulaOneItem formulaOneItem) {
        List<FormulaOneItem> formulaOneItems = (repository.findAll()
                .stream().map(this::buildFormulaOneItem)
                .toList());
        return formulaOneItems.stream().anyMatch(item -> item.equals(formulaOneItem));
    }

    private FormulaOneItem buildFormulaOneItem(FormulaOneItemEntity entity) {
        return FormulaOneItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .foundationYear(entity.getFoundationYear())
                .championships(entity.getChampionships())
                .entryFeeStatus(entity.getEntryFeeStatus())
                .build();
    }
}
