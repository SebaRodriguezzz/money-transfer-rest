package io.datajek.moneytransferrest.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
