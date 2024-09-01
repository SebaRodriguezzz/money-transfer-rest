package moneytransfer.business;

import moneytransfer.persistence.entity.TransactionEntity;
import moneytransfer.persistence.entity.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount);
    TransactionEntity findById(Long id);
    List<TransactionEntity> findAll();
    void delete(Long id);
    List<TransactionEntity> findByType(UserEntity user, String type);
}
