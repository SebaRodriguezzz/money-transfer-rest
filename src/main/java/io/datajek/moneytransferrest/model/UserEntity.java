package io.datajek.moneytransferrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nationality;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    //TODO: accountentity, un usuario puede tener varias cuentas. lista de cuentas
    private BigDecimal balance;
    private long accountNumber;

    @OneToMany(mappedBy = "sender")
    private List<TransactionEntity> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<TransactionEntity> receivedTransactions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id", referencedColumnName = "id")
    private UserCredentialsEntity credentials;


    public UserEntity(){}
    public UserEntity(String name, String nationality, Date birthDate, BigDecimal balance) {
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.balance = balance;
        this.accountNumber = setAccountNumber();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthday) {
        this.birthDate = birthday;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserCredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentialsEntity credentials) {
        this.credentials = credentials;
    }

    public void addBalance(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<TransactionEntity> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<TransactionEntity> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<TransactionEntity> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<TransactionEntity> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public long setAccountNumber() {
        long min = 10000000000000000L;
        long max = 99999999999999999L;

        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
