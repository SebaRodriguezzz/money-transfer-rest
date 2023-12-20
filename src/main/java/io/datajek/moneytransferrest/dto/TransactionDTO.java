package io.datajek.moneytransferrest.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    private long id;
    private Instant date;
    private long  senderAccountNumber;
    private long receiverAccountNumber;
    private BigDecimal amount;


    public TransactionDTO(long id, Instant date, long senderAccountNumber, long receiverAccountNumber, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
    }

    public TransactionDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(long receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
