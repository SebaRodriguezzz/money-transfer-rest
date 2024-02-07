package io.datajek.moneytransferrest.business.impl;

import io.datajek.moneytransferrest.business.TransactionService;
import io.datajek.moneytransferrest.exception.transaction.TransactionFailedException;
import io.datajek.moneytransferrest.exception.transaction.TransactionNotFoundException;
import io.datajek.moneytransferrest.persistence.TransactionPersistence;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserPersistence userPersistence;
    private final TransactionPersistence transactionPersistence;

    public TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        try {
            receiver.addBalance(amount);
            sender.subtractBalance(amount);
            userPersistence.save(receiver);
            userPersistence.save(sender);
            return recordTransaction(receiver, sender, amount);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed: " + e.getMessage());
        }
    }

    private TransactionEntity recordTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        TransactionEntity transaction = new TransactionEntity(Instant.now(), sender, receiver, amount);
        return transactionPersistence.save(transaction);
    }

    private List<TransactionEntity> findByAccountNumber(Long accountNumber) {
        List<TransactionEntity> transactions = transactionPersistence.findBySenderAccountNumber(accountNumber);
        transactions.addAll(transactionPersistence.findByReceiverAccountNumber(accountNumber));
        return transactions;
    }

    private List<TransactionEntity> findBySenderAccountNumber(Long accountNumber) {
        return transactionPersistence.findBySenderAccountNumber(accountNumber);
    }

    private List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber) {
        return transactionPersistence.findByReceiverAccountNumber(accountNumber);
    }

    public List<TransactionEntity> findByType(UserEntity user, String type) {
        Long accountNumber = user.getAccountNumber();
        if (type != null) {
            if (type.equals("sent")) {
                return findBySenderAccountNumber(accountNumber);
            } else if (type.equals("received")) {
                return findByReceiverAccountNumber(accountNumber);
            }
        }
        return findByAccountNumber(accountNumber);
    }

    public TransactionEntity findById(Long id) {
        return transactionPersistence.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id {" + id + "} not found"));
    }

    public List<TransactionEntity> findAll() {
        return transactionPersistence.findAll();
    }

    public void delete(Long id) {
        transactionPersistence.deleteById(id);
    }


}
