package com.projects.arch_ref.interfaces.http.inbound.handler;

import com.projects.arch_ref.domain.exceptions.ConflictException;
import com.projects.arch_ref.interfaces.http.inbound.dto.ExceptionDetails;
import com.projects.arch_ref.interfaces.http.inbound.dto.ValidationExceptionDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllExceptionHandlerTest {

    @InjectMocks
    private AllExceptionHandler handler;

    @Test
    void shouldReturnStatus500WhenExceptionOccurs() {
        ResponseEntity<ExceptionDetails> res = handler.handleException(new RuntimeException());

        assertEquals(500, res.getStatusCodeValue());
    }

    @Test
    void shouldReturnStatus409WhenConflictExceptionOccurs() {
        ResponseEntity<ExceptionDetails> res = handler.handleConflictException(new ConflictException("any"));

        assertEquals(409, res.getStatusCodeValue());
    }

    @Test
    void shouldReturnStatus400WhenMethodArgumentNotValidExceptionOccurs() {
        FieldError fieldError = new FieldError("any_object", "any_field", "any_message");
        BindingResult bindingResult = mock(BindingResult.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        WebRequest webRequest = mock(WebRequest.class);

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);
        ResponseEntity<Object> res = handler.handleMethodArgumentNotValid(exception, headers, status, webRequest);

        assertEquals(400, res.getStatusCodeValue());

        ValidationExceptionDetails body = (ValidationExceptionDetails) res.getBody();

        assert body != null;
        assertTrue(body.getViolations().containsKey("any_field"));
        assertEquals(body.getViolations().get("any_field"), "any_message");
    }

}
