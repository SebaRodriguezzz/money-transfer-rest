package moneytransfer.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import moneytransfer.model.NationalityEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private NationalityEnum nationality;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false, updatable = false)
    private Long accountNumber;

    @Column
    @OneToMany(mappedBy = "sender")
    private List<TransactionEntity> sentTransactions;

    @Column
    @OneToMany(mappedBy = "receiver")
    private List<TransactionEntity> receivedTransactions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id", nullable = false)
    private UserCredentialsEntity credentials;

    public UserEntity(String name, NationalityEnum nationality, Date birthDate, BigDecimal balance, Long accountNumber, UserCredentialsEntity credentials) {
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.credentials = credentials;
    }
    public void addBalance(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
    }

}
