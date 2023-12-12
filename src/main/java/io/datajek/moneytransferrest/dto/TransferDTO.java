package io.datajek.moneytransferrest.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class TransferDTO {
    public Instant date;
    public String sender;
    public String receiver;
    public BigDecimal amount;

    public TransferDTO(Instant date, String sender, String receiver, BigDecimal amount) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
