package moneytransfer.business.impl;

import moneytransfer.business.TransactionService;
import moneytransfer.exception.transaction.TransactionFailedException;
import moneytransfer.exception.transaction.TransactionNotFoundException;
import moneytransfer.persistence.UserPersistence;
import moneytransfer.persistence.entity.TransactionEntity;
import moneytransfer.persistence.entity.UserEntity;
import moneytransfer.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserPersistence userPersistence;
    private final TransactionRepository transactionRepository;

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
        return transactionRepository.save(transaction);
    }

    public List<TransactionEntity> findByType(UserEntity user, String type) {
        Long accountNumber = user.getAccountNumber();
        if (type != null) {
            if (type.equals("sent")) {
                return transactionRepository.findBySenderAccountNumber(accountNumber);
            } else if (type.equals("received")) {
                return transactionRepository.findByReceiverAccountNumber(accountNumber);
            }
        }
        return findByAccountNumber(accountNumber);
    }

    private List<TransactionEntity> findByAccountNumber(Long accountNumber) {
        List<TransactionEntity> transactions = new ArrayList<>(transactionRepository.findBySenderAccountNumber(accountNumber));
        transactions.addAll(transactionRepository.findByReceiverAccountNumber(accountNumber));
        return transactions;
    }

    public TransactionEntity findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id {" + id + "} not found"));
    }

    public List<TransactionEntity> findAll() {
        return transactionRepository.findAll();
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
