package com.projects.arch_ref.interfaces.http.inbound.controler.api;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.interfaces.http.inbound.dto.ExceptionDetails;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonSearchDTO;
import com.projects.arch_ref.interfaces.http.inbound.dto.ValidationExceptionDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/person")
@Tag(name = "Person")
public interface PersonAPI {

    @PostMapping
    @Operation(summary = "Creates a new Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new person with success"),
            @ApiResponse(responseCode = "400", description = "Invalid attributes provided", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidationExceptionDetails.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict Exception", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionDetails.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error Exception", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionDetails.class))})
    })
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> create(@Valid @RequestBody PersonRequest personRequest);

    @GetMapping("/{id}")
    @Operation(summary = "Search a person by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a person with provided id", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid attributes provided", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidationExceptionDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found Exception", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionDetails.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error Exception", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionDetails.class))})
    })
    ResponseEntity<Person> findById(@PathVariable Long id);

    @GetMapping()
    @Operation(summary = "Search a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a new person with success", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PersonSearchResponse.class))}),
            @ApiResponse(responseCode = "204", description = "Not found Person with provided search details"),
            @ApiResponse(responseCode = "400", description = "Invalid attributes provided", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ValidationExceptionDetails.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error Exception", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionDetails.class))})
    })
    ResponseEntity<PersonSearchResponse> findBySearch(PersonSearchDTO personSearch);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> delete(@PathVariable Long id);

}
