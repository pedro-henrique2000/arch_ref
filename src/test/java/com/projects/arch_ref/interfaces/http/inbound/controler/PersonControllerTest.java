package com.projects.arch_ref.interfaces.http.inbound.controler;

import com.projects.arch_ref.application.CreatePerson;
import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonMapperDTO;
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

}
