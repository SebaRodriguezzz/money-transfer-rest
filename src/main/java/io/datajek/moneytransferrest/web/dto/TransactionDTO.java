package io.datajek.moneytransferrest.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Schema(description = "DTO to represent transaction information")
public class TransactionDTO {

    @Schema(description = "Transaction ID", example = "1")
    private long id;

    @Schema(description = "Transaction date", example = "2024-02-04T12:30:45Z")
    private Instant date;

    @Schema(description = "Sender's account number", example = "123456789")
    private long senderAccountNumber;

    @Schema(description = "Receiver's account number", example = "987654321")
    private long receiverAccountNumber;

    @Schema(description = "Transaction amount", example = "500.75")
    private BigDecimal amount;

}
