package io.datajek.moneytransferrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//TODO: cambiar los long
@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NationalityEnum nationality;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false, updatable = false)
    private long accountNumber;

    @Column
    @OneToMany(mappedBy = "sender")
    private List<TransactionEntity> sentTransactions;

    @Column
    @OneToMany(mappedBy = "receiver")
    private List<TransactionEntity> receivedTransactions;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    private UserCredentialsEntity credentials;

    public void addBalance(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
    }
}
