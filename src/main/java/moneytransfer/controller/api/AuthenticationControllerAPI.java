package moneytransfer.controller.api;

import moneytransfer.dto.CredentialsDTO;
import moneytransfer.repository.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerAPI {

    @Operation(summary = "Register a new user", description = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody UserEntity user);

    @Operation(summary = "Login", description = "Authenticate user and create a session")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody CredentialsDTO credentials, HttpSession session);

    @Operation(summary = "Logout", description = "Terminate the user session")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logout successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/logout")
    ResponseEntity<String> logout(HttpSession session);
}