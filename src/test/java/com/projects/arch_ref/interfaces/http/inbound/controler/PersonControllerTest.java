package com.projects.arch_ref.interfaces.http.inbound.controler;

import com.projects.arch_ref.application.CreatePerson;
import com.projects.arch_ref.application.FindPersonById;
import com.projects.arch_ref.application.FindPersonBySearch;
import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonSearchDTO;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonMapperDTO;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonSearchMapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private CreatePerson createPerson;

    @Mock
    private PersonMapperDTO personMapperDto;

    @Mock
    private PersonSearchMapperDTO personSearchMapperDTO;

    @Mock
    private FindPersonBySearch findPersonBySearch;

    @Mock
    private FindPersonById findPersonById;

    private PersonRequest personRequest;

    private Person mappedPerson;

    @BeforeEach
    void setup() {
        personRequest = mock(PersonRequest.class);
        mappedPerson = mock(Person.class);

        when(personMapperDto.toEntity(personRequest)).thenReturn(mappedPerson);
        when(createPerson.invoke(mappedPerson)).thenReturn(1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void shouldCallMapperWithCorrectValue() {
        personController.create(personRequest);
        verify(personMapperDto, times(1)).toEntity(personRequest);
    }

    @Test
    void shouldCallCreatePersonWithCorrectValue() {
        personController.create(personRequest);
        verify(createPerson, times(1)).invoke(mappedPerson);
    }

    @Test
    void shouldThrowsIfCreatePersonThrows() {
        when(createPerson.invoke(any(Person.class))).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> personController.create(personRequest));
    }

    @Test
    void shouldReturnLocationHeaderAndStatus201OnSuccess() {
        ResponseEntity<Void> responseEntity = assertDoesNotThrow(() -> personController.create(personRequest));

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getHeaders().get("Location").get(0).equals("http://localhost/1"));
    }

    @Test
    void shouldCallFindByIdWithCorrectParam() {
        Person person = mock(Person.class);
        when(findPersonById.invoke(1L)).thenReturn(person);
        ResponseEntity<Person> responseEntity = assertDoesNotThrow(() -> personController.findById(1L));

        verify(findPersonById, times(1)).invoke(1L);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(person, responseEntity.getBody());
    }

    @Test
    void shouldReturnStatus200() {
        PersonSearchDTO personSearchDTO = mock(PersonSearchDTO.class);
        PersonSearch personSearch = mock(PersonSearch.class);
        PersonSearchResponse searchResponse = mock(PersonSearchResponse.class);
        List<Person> personList = mock(List.class);

        when(personSearchMapperDTO.toEntity(personSearchDTO)).thenReturn(personSearch);
        when(findPersonBySearch.invoke(personSearch)).thenReturn(searchResponse);
        when(findPersonBySearch.invoke(personSearch)).thenReturn(searchResponse);
        when(searchResponse.getPersons()).thenReturn(personList);
        when(personList.isEmpty()).thenReturn(false);

        ResponseEntity<PersonSearchResponse> responseEntity = assertDoesNotThrow(() -> personController.findBySearch(personSearchDTO));

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(searchResponse, responseEntity.getBody());
    }

    @Test
    void shouldReturnStatus204() {
        PersonSearchDTO personSearchDTO = mock(PersonSearchDTO.class);
        PersonSearch personSearch = mock(PersonSearch.class);
        PersonSearchResponse searchResponse = mock(PersonSearchResponse.class);
        List<Person> personList = mock(List.class);

        when(personSearchMapperDTO.toEntity(personSearchDTO)).thenReturn(personSearch);
        when(findPersonBySearch.invoke(personSearch)).thenReturn(searchResponse);
        when(findPersonBySearch.invoke(personSearch)).thenReturn(searchResponse);
        when(searchResponse.getPersons()).thenReturn(personList);
        when(personList.isEmpty()).thenReturn(true);

        ResponseEntity<PersonSearchResponse> responseEntity = assertDoesNotThrow(() -> personController.findBySearch(personSearchDTO));

        assertEquals(204, responseEntity.getStatusCodeValue());
        assertEquals(searchResponse, responseEntity.getBody());
    }

}
