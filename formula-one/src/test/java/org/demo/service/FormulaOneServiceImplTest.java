package org.demo.service;

import org.apache.coyote.BadRequestException;
import org.demo.error.InvalidTeamException;
import org.demo.error.InvalidRequestException;
import org.demo.model.DeleteResponse;
import org.demo.model.EntryFeeStatus;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.demo.persistence.repository.FormulaOneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FormulaOneServiceImplTest {
    @InjectMocks
    FormulaOneServiceImpl formulaOneService;

    @Mock
    private FormulaOneRepository repository;

    @Mock
    private MapperService mapperService;

    @Mock
    private ValidationService validationService;

    String id;

    FormulaOneItem formulaOneItem;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        formulaOneService = new FormulaOneServiceImpl(repository, mapperService, validationService);
        id = "e2af100b-6f3c-4dba-8f65-322ab5c35dea";
        formulaOneItem = FormulaOneItem.builder()
                .id(UUID.fromString(id))
                .name("Ferrari")
                .foundationYear("1950")
                .championships(16).entryFeeStatus(EntryFeeStatus.PAID)
                .build();
    }

    @Test
    public void getTeams_shouldReturnExpected() {
        // given
        List<FormulaOneItemEntity> formulaOneItemEntities = new ArrayList<>();
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity(UUID.fromString(id), "Ferrari", "1950",
                16, EntryFeeStatus.PAID);
        formulaOneItemEntities.add(formulaOneItemEntity);
        Mockito.when(repository.findAll())
                .thenReturn(formulaOneItemEntities);
        Mockito.when(mapperService.toDomain(formulaOneItemEntity))
                .thenReturn(formulaOneItem);
        // when
        List<FormulaOneItem> result = formulaOneService.getTeams();
        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(UUID.fromString(id));
        assertThat(result.get(0).getName()).isEqualTo("Ferrari");
        assertThat(result.get(0).getFoundationYear()).isEqualTo("1950");
        assertThat(result.get(0).getEntryFeeStatus()).isEqualTo(EntryFeeStatus.PAID);
        Mockito.verify(this.repository, Mockito.times(1)).findAll();
    }

    @Test
    public void addTeam_shouldCallRepository() throws BadRequestException {
        // given
        Mockito.when(repository.existsByNameIgnoreCaseIn("Ferrari"))
                .thenReturn(Optional.of(false));
        Mockito.when(validationService.foundationYearIsValid(formulaOneItem))
                .thenReturn(true);
        // when
        FormulaOneItem result = formulaOneService.addTeam(formulaOneItem);
        // then
        assertThat(result.getId()).isEqualTo(UUID.fromString(id));
        assertThat(result.getName()).isEqualTo("Ferrari");
        assertThat(result.getFoundationYear()).isEqualTo("1950");
        assertThat(result.getEntryFeeStatus()).isEqualTo(EntryFeeStatus.PAID);
        Mockito.verify(this.repository, Mockito.times(1)).existsByNameIgnoreCaseIn("Ferrari");
    }

    @Test
    public void addExistingTeam_shouldReturnInvalidException() {
        // given
        Mockito.when(repository.existsByNameIgnoreCaseIn("Ferrari"))
                .thenReturn(Optional.of(true));
        // when
        Exception exception = assertThrows(InvalidTeamException.class, () -> {
            formulaOneService.addTeam(formulaOneItem);
        });
        // then
        assertTrue(exception.getMessage().contains("The Team is in the list"));
    }

    @Test
    public void updateTeam_shouldReturnItem() {
        // given
        Mockito.when(repository.existsById(UUID.fromString(id)))
                .thenReturn(true);
        Mockito.when(validationService.foundationYearIsValid(formulaOneItem))
                .thenReturn(true);
        // when
        FormulaOneItem result = formulaOneService.updateTeam(formulaOneItem);
        // then
        assertThat(result.getId()).isEqualTo(UUID.fromString(id));
        assertThat(result.getName()).isEqualTo("Ferrari");
        assertThat(result.getFoundationYear()).isEqualTo("1950");
        assertThat(result.getEntryFeeStatus()).isEqualTo(EntryFeeStatus.PAID);
        Mockito.verify(this.repository, Mockito.times(1)).existsById(UUID.fromString(id));
    }

    @Test
    public void updateMissingTeam_shouldReturnTeamNotFoundException() {
        // given
        Mockito.when(repository.existsById(UUID.fromString(id)))
                .thenReturn(false);
        // when
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            formulaOneService.updateTeam(formulaOneItem);
        });
        // then
        assertThat(exception.getMessage()).isEqualTo(String.format("Team cannot be found with this ID: %s", id));
        Mockito.verify(this.repository, Mockito.times(1)).existsById(UUID.fromString(id));
    }

    @Test
    public void updateMissingTeam_shouldReturnInvalidException() {
        // given
        List<FormulaOneItemEntity> formulaOneItemEntities = new ArrayList<>();
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity(UUID.fromString("b2af100b-6f3c-4dba-8f65-322ab5c35dea"), "Ferrari", "1950",
                16, EntryFeeStatus.PAID);
        formulaOneItemEntities.add(formulaOneItemEntity);
        Mockito.when(repository.existsById(UUID.fromString(id)))
                .thenReturn(true);
        Mockito.when(repository.findByNameIgnoreCaseIn("Ferrari"))
                .thenReturn(Optional.of(formulaOneItemEntities));
        // when
        Exception exception = assertThrows(InvalidTeamException.class, () -> {
            formulaOneService.updateTeam(formulaOneItem);
        });
        // then
        assertTrue(exception.getMessage().contains("The Team is in the list"));
        Mockito.verify(this.repository, Mockito.times(1)).existsById(UUID.fromString(id));
    }

    @Test
    public void deleteTeam_shouldReturnResponse() {
        // given
        Mockito.when(repository.existsById(UUID.fromString(id)))
                .thenReturn(true);
        // when
        DeleteResponse result = formulaOneService.deleteTeam(id);
        // then
        assertThat(result.getMessage()).isEqualTo(String.format("Team deleted with ID: %s", id));
        Mockito.verify(this.repository, Mockito.times(1)).existsById(UUID.fromString(id));
    }

    @Test
    public void deleteMissingTeam_shouldReturnTeamNotFoundException() {
        // given
        Mockito.when(repository.existsById(UUID.fromString(id)))
                .thenReturn(false);
        // when
        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            formulaOneService.deleteTeam(id);
        });
        // then
        assertThat(exception.getMessage()).isEqualTo(String.format("Team cannot be found with this ID: %s", id));
        Mockito.verify(this.repository, Mockito.times(1)).existsById(UUID.fromString(id));
    }
}
