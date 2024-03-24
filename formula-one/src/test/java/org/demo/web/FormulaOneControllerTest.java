package org.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.model.EntryFeeStatus;
import org.demo.model.FormulaOneItem;
import org.demo.persistence.entity.FormulaOneItemEntity;
import org.demo.persistence.repository.FormulaOneRepository;
import org.demo.service.MapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class FormulaOneControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @MockBean
    FormulaOneRepository repository;

    @MockBean
    MapperService mapperService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void whenCallingWrongPath_thenStatusCodeShouldBe404() throws Exception {
        // given
        String path = "/api/f1team";
        // when
        ResultActions request = mockMvc.perform(get(path)).andDo(print());
        //then
        request.andExpect(status().isNotFound());
    }

    @Test
    void whenCallingGetTeams_thenShouldReturnExpectedJsonResponse() throws Exception {
        // given
        String path = "/api/f1";
        List<FormulaOneItemEntity> entities = new ArrayList<>();
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity(UUID.fromString("e2af100b-6f3c-4dba-8f65-322ab5c35dea"), "Ferrari", "1950",
                16, EntryFeeStatus.PAID);
        entities.add(formulaOneItemEntity);
        FormulaOneItem formulaOneItem = FormulaOneItem.builder()
                .id(UUID.fromString("05431da2-1df6-4812-b7e8-d4625710c87a"))
                .name("Ferrari")
                .foundationYear("1950")
                .championships(16).entryFeeStatus(EntryFeeStatus.PAID)
                .build();
        String jsonResponse = """
                [
                    {
                        "id":"05431da2-1df6-4812-b7e8-d4625710c87a",
                        "name":"Ferrari",
                        "foundationYear":"1950",
                        "championships":16,
                        "entryFeeStatus":PAID
                    }
                ]
                """;
        // when
        when(repository.findAll()).thenReturn(entities);
        when(mapperService.toDomain(formulaOneItemEntity)).thenReturn(formulaOneItem);
        ResultActions request = mockMvc.perform(get(path)).andDo(print());
        //then
        request.andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void whenCallingAddTeam_thenShouldReturnAddedTeamData() throws Exception {
        // given
        String path = "/api/f1/team";
        FormulaOneItem formulaOneItem = FormulaOneItem
                .builder()
                .id(null)
                .name("Ferrari")
                .foundationYear("1950")
                .championships(16)
                .entryFeeStatus(EntryFeeStatus.PAID)
                .build();
        // when
        ResultActions request = mockMvc.perform(Objects.requireNonNull(post(path)
                        .content(asJsonString(formulaOneItem))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andDo(print());
        //then
        request.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void whenCallingUpdateTeamWithNoTeamData_thenStatusReturnInternalServerError() throws Exception {
        // given
        String path = "/api/f1/team";
        String id = "05431da2-1df6-4812-b7e8-d4625710c87a";
        FormulaOneItem formulaOneItem = FormulaOneItem
                .builder()
                .id(UUID.fromString(id))
                .name("Ferrari")
                .foundationYear("1950")
                .championships(16)
                .entryFeeStatus(EntryFeeStatus.PAID)
                .build();
        // when
        ResultActions request = mockMvc.perform(Objects.requireNonNull(put(path)
                        .content(asJsonString(formulaOneItem))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andDo(print());
        //then
        request.andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(String.format("Team cannot be found with this ID: %s", id)));
    }

    @Test
    void whenCallingUpdateTeam_thenReturnsChangedTeamData() throws Exception {
        // given
        String path = "/api/f1/team";
        String id = "05431da2-1df6-4812-b7e8-d4625710c87a";
        FormulaOneItemEntity formulaOneItemEntity = new FormulaOneItemEntity(UUID.fromString(id), "Ferrari", "1950",
                16, EntryFeeStatus.PAID);
        FormulaOneItem updateItem = FormulaOneItem
                .builder()
                .id(UUID.fromString(id))
                .name("Ferrari")
                .foundationYear("1950")
                .championships(18)
                .entryFeeStatus(EntryFeeStatus.NOT_PAID)
                .build();

        Mockito.when(repository.existsById(UUID.fromString(id))).thenReturn(true);
        Mockito.when(repository.getReferenceById(UUID.fromString(id))).thenReturn(formulaOneItemEntity);
        // when
        ResultActions request = mockMvc.perform(put(path)
                        .content(asJsonString(updateItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        //then
        request.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.championships").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entryFeeStatus").value("NOT_PAID"));
    }

    @Test
    void whenCallingDeleteTeamWithNonExistingId_thenStatusReturnInternalServerError() throws Exception {
        // given
        String id = "05431da2-1df6-4812-b7e8-d4625710c87a";
        String path = "/api/f1/team/" + id;
        // when
        ResultActions request = mockMvc.perform(Objects.requireNonNull(delete(path)))
                .andDo(print());
        //then
        request.andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(String.format("Team cannot be found with this ID: %s", id)));
    }

    @Test
    void whenCallingDeleteTeamWithExistingId_thenShouldRemoveTeam() throws Exception {
        // given
        String id = "05431da2-1df6-4812-b7e8-d4625710c87a";
        String path = "/api/f1/team/" + id;
        Mockito.when(repository.existsById(UUID.fromString(id))).thenReturn(true);
        // when
        ResultActions request = mockMvc.perform(Objects.requireNonNull(delete(path)))
                .andDo(print());
        //then
        request.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(String.format("Team deleted with ID: %s", id)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
