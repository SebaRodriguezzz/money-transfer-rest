package io.datajek.moneytransferrest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String nationality;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private BigDecimal balance;
    private long accountNumber;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credentials_id")
    private UserCredentials credentials;


    public UserEntity(){}
    public UserEntity(String name, String nationality, Date birthDate, BigDecimal balance) {
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.balance = balance;
        this.accountNumber = setAccountNumber();
    }

    public int getId() {
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

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
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

    public long setAccountNumber() {
        long min = 10000000000000000L;
        long max = 99999999999999999L;

        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
