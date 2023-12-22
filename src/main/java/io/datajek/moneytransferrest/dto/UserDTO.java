package io.datajek.moneytransferrest.dto;

import java.math.BigDecimal;
import java.util.Date;

public class UserDTO {
    private long id;
    private String name;
    private String nationality;
    private Date birthDate;
    private Long accountNumber;
    private BigDecimal balance;

    public UserDTO() {
    }

    public UserDTO(long id, String name, String nationality, Date birthDate, Long accountNumber, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
