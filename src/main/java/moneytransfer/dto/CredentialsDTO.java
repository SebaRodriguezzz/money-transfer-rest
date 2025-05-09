package moneytransfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO to represent user credentials")
public class CredentialsDTO {

    @Schema(description = "Username", example = "john.doe")
    private String username;

    @Schema(description = "Password", example = "password123")
    private String password;
}
