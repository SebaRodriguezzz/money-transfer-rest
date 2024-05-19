package io.datajek.moneytransferrest.persistence.impl;

import io.datajek.moneytransferrest.persistence.TransactionPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionPersistenceImpl  implements TransactionPersistence {

    private final TransactionRepository transactionRepository;

    public TransactionPersistenceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionEntity> findBySenderAccountNumber(Long accountNumber) {
        return transactionRepository.findBySenderAccountNumber(accountNumber);
    }

    public List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber) {
        return transactionRepository.findByReceiverAccountNumber(accountNumber);
    }

    public TransactionEntity save(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<TransactionEntity> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<TransactionEntity> findAll() {
        return transactionRepository.findAll();
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}