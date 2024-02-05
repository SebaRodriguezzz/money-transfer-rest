package io.datajek.moneytransferrest.controller.api;


import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionControllerAPI {

    @Operation(summary = "Find transaction by ID", description = "Get a transaction by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<TransactionDTO> findById(@Parameter(description = "Transaction ID", required = true) @PathVariable Long id);

    @Operation(summary = "Get all transactions", description = "Get a list of all transactions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class))))
    })
    @GetMapping
    ResponseEntity<List<TransactionDTO>> findAll();

    @Operation(summary = "Delete transaction by ID", description = "Delete a transaction by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@Parameter(description = "Transaction ID", required = true) @PathVariable Long id);
}