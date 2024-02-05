package io.datajek.moneytransferrest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, updatable = false)
    private Instant date;
    @Column(nullable = false, updatable = false)
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @Column(nullable = false, updatable = false)
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;
    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    public TransactionEntity(Instant date, UserEntity sender, UserEntity receiver, BigDecimal amount) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
