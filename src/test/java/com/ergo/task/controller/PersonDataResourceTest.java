package com.ergo.task.controller;

import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.specification.PersonSearchCriteria;
import com.ergo.task.service.PersonDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ergo.task.TestUtils.getContentFromFile;
import static com.ergo.task.enums.PersonGender.MALE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PersonDataResourceTest {
    private static final String API_URL = "/persons";
    public static final String FIND_PERSON_BY = "/findPersonBy";

    @Mock
    private PersonDataServiceImpl mockService;

    @InjectMocks
    private PersonDataResource controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createPerson() throws Exception {
        var request = getContentFromFile("/input/newPerson.json");

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        verify(mockService).addPersonData(any(PersonDataDTO.class));
    }

    @Test
    void createPersonFailed() throws Exception {
        var request = getContentFromFile("/input/notValidPerson.json");

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        verifyNoInteractions(mockService);
    }

    @Test
    void findPersonBy() throws Exception {
        var request = getContentFromFile("/input/findPersonByName.json");
        var response = response();

        when(mockService.findPersonBy(any(PersonSearchCriteria.class))).thenReturn(Optional.of(response));

        mockMvc.perform(
                        get(API_URL + FIND_PERSON_BY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(mockService).findPersonBy(any(PersonSearchCriteria.class));
    }

    @Test
    void findPersonByReturnNotFound() throws Exception {
        var request = getContentFromFile("/input/findPersonByName.json");

        mockMvc.perform(
                        get(API_URL + FIND_PERSON_BY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        verify(mockService).findPersonBy(any(PersonSearchCriteria.class));
    }

    @Test
    void findAllPersons() throws Exception {
        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(List.of().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(mockService).fetchAllPersons();
    }

    @Test
    void updatePersonData() throws Exception {
        var request = getContentFromFile("/input/updatedPerson.json");

        mockMvc.perform(
                        put(API_URL + "/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(mockService).updatePersonData(any(PersonDataDTO.class), eq(1L));
    }

    @Test
    void updatePersonDataFailedNoProvidedId() throws Exception {
        var request = getContentFromFile("/input/updatedPerson.json");

        mockMvc.perform(
                        put(API_URL + "/{id}", "")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andReturn();

        verifyNoInteractions(mockService);
    }

    @Test
    void deletePersonById() throws Exception {
        mockMvc.perform(
                        delete(API_URL + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(mockService).deletePersonData(1L);
    }

    @Test
    void deletePersonFailedWithoutId() throws Exception {
        mockMvc.perform(
                        delete(API_URL + "/{id}", ""))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(mockService);
    }

    private PersonDataDTO response() {
        return PersonDataDTO.builder()
                .personalId("")
                .firstName("Oleg")
                .lastName("")
                .dateOfBirth(LocalDate.of(1999, 12, 12))
                .gender(MALE)
                .phoneNumber("")
                .email("")
                .build();
    }
}