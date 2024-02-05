package io.datajek.moneytransferrest.persistence.impl;

import io.datajek.moneytransferrest.persistence.TransactionPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TransactionPersistenceImpl  implements TransactionPersistence {

    private final TransactionPersistence transactionPersistence;

    public List<TransactionEntity> findBySenderAccountNumber(Long accountNumber) {
        return transactionPersistence.findBySenderAccountNumber(accountNumber);
    }

    public List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber) {
        return transactionPersistence.findByReceiverAccountNumber(accountNumber);
    }

    public TransactionEntity save(TransactionEntity transaction) {
        return transactionPersistence.save(transaction);
    }

    public Optional<TransactionEntity> findById(Long id) {
        return transactionPersistence.findById(id);
    }

    public List<TransactionEntity> findAll() {
        return transactionPersistence.findAll();
    }

    public void deleteById(Long id) {
        transactionPersistence.deleteById(id);
    }
}
