package io.datajek.moneytransferrest.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class TransferDTO {
    private Instant date;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
    private BigDecimal amount;

    public TransferDTO(Instant date, Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount) {
        this.date = date;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
