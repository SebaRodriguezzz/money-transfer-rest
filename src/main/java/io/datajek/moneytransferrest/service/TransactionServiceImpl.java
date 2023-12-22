package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.exception.transaction.TransactionFailedException;
import io.datajek.moneytransferrest.exception.transaction.TransactionNotFoundException;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.TransactionRepository;
import io.datajek.moneytransferrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    public TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        try {
            receiver.addBalance(amount);
            sender.subtractBalance(amount);
            userRepository.save(receiver);
            userRepository.save(sender);
            return recordTransaction(receiver, sender, amount);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed: " + e.getMessage());
        }
    }

    private TransactionEntity recordTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        TransactionEntity transaction = new TransactionEntity(Instant.now(), sender, receiver, amount);
        return transactionRepository.save(transaction);
    }

    public List<TransactionEntity> findByAccountNumber(Long accountNumber) {
        List<TransactionEntity> transactions = transactionRepository.findBySenderAccountNumber(accountNumber);
        transactions.addAll(transactionRepository.findByReceiverAccountNumber(accountNumber));
        return transactions;
    }

    public List<TransactionEntity> findBySenderAccountNumber(Long accountNumber) {
        return transactionRepository.findBySenderAccountNumber(accountNumber);
    }

    public List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber) {
        return transactionRepository.findByReceiverAccountNumber(accountNumber);
    }

    public List<TransactionEntity> findAll() {
        return transactionRepository.findAll();
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }



}
