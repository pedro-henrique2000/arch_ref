package com.projects.arch_ref.interfaces.http.inbound.handler;

import com.projects.arch_ref.domain.exceptions.ConflictException;
import com.projects.arch_ref.interfaces.http.inbound.dto.ExceptionDetails;
import com.projects.arch_ref.interfaces.http.inbound.dto.ValidationExceptionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AllExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDetails> handleConflictException(final ConflictException conflictException) {
        conflictException.printStackTrace();

        log.error("Conflict Exception occurred");

        final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(HttpStatus.CONFLICT.value())
                .details(conflictException.getMessage())
                .title("Conflict Exception")
                .timestamp(LocalDateTime.now())
                .developerMessage(conflictException.getClass().getName())
                .build();

        return ResponseEntity.status(409).body(exceptionDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Fields Error {}", methodArgumentNotValidException.getAllErrors());
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        Map<String, String> violations = getViolations(fieldErrors);
        ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
                .details("Check the wrong fields")
                .timestamp(LocalDateTime.now())
                .title("Field Validation Exception")
                .developerMessage(methodArgumentNotValidException.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value())
                .violations(violations)
                .build();
        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleException(final Exception exception) {
        log.error("Unexpected error ocurred. Message: {}", exception.getMessage());
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .details("Unexpected error occurred.")
                .title("Internal Server Error")
                .timestamp(LocalDateTime.now())
                .developerMessage(exception.getClass().getName())
                .build();
        return ResponseEntity.internalServerError().body(exceptionDetails);
    }

    private Map<String, String> getViolations(List<FieldError> fieldErrors) {
        Map<String, String> violations = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            violations.put(field, message);
        }
        return violations;
    }

}
