package moneytransfer.persistence;

import moneytransfer.persistence.entity.TransactionEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionPersistence {

    List<TransactionEntity> findBySenderAccountNumber(Long accountNumber);
    List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber);
    TransactionEntity save(TransactionEntity transaction);
    Optional<TransactionEntity> findById(Long id);
    List<TransactionEntity> findAll();
    void deleteById(Long id);
}
