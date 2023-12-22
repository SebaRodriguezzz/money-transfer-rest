package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount);
    List<TransactionEntity> findAll();
    void delete(Long id);
    List<TransactionEntity> findByAccountNumber(Long accountNumber);
    List<TransactionEntity> findBySenderAccountNumber(Long accountNumber);
    List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber);
}
