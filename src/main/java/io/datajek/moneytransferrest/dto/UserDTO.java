package io.datajek.moneytransferrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Schema(description = "DTO to represent user information")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    @NonNull
    private Long id;

    @Schema(description = "User name", example = "John Doe")
    private String name;

    @Schema(description = "User nationality", example = "USA")
    private String nationality;

    @Schema(description = "User birth date", example = "1990-01-01")
    private Date birthDate;

    @Schema(description = "User account number", example = "123456789")
    private Long accountNumber;

    @Schema(description = "User actual balance", example = "1000.50")
    private BigDecimal balance;
}