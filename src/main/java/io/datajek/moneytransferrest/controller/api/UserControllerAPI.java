package io.datajek.moneytransferrest.controller.api;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

public interface UserControllerAPI {

    @Operation(summary = "Transfer money", description = "Transfer money between users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful money transfer",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class)))
    })
    @PostMapping("/transfer")
    ResponseEntity<TransactionDTO> transferMoney(@RequestBody TransactionDTO transactionDTO, HttpSession session);

    @Operation(summary = "Get user transactions", description = "Get a list of user transactions based on type (sent or received)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class))))
    })
    @GetMapping("/transactions")
    ResponseEntity<List<TransactionDTO>> getTransactions(HttpSession session,
                                                         @Parameter(description = "Transaction type (sent or received)", required = false)
                                                         @RequestParam(required = false) String type);

    @Operation(summary = "Find user by ID", description = "Get a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@Parameter(description = "User ID", required = true) @PathVariable Long id);

    @Operation(summary = "Get all users", description = "Get a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))
    })
    @GetMapping
    ResponseEntity<List<UserDTO>> findAll();

    @Operation(summary = "Create a new user", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping
    ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO);

    @Operation(summary = "Update user information", description = "Update user information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDTO> update(@Parameter(description = "User ID", required = true) @PathVariable Long id, @RequestBody UserDTO userDTO);

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@Parameter(description = "User ID", required = true) @PathVariable Long id);
}