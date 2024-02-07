package io.datajek.moneytransferrest.business;

import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount);
    TransactionEntity findById(Long id);
    List<TransactionEntity> findAll();
    void delete(Long id);
    List<TransactionEntity> findByType(UserEntity user, String type);
}
