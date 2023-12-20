package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.exception.TransactionFailedException;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionService {

    private final UserRepository userRepo;

    @Autowired
    public TransactionService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional(rollbackFor = TransactionFailedException.class)
    public TransactionEntity performTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        try {
            receiver.addBalance(amount);
            sender.subtractBalance(amount);
            userRepo.save(receiver);
            userRepo.save(sender);
            return recordTransaction(receiver, sender, amount);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed: " + e.getMessage());
        }
    }
    
    private TransactionEntity recordTransaction(UserEntity receiver, UserEntity sender, BigDecimal amount) {
        return new TransactionEntity(Instant.now(), sender, receiver, amount);
    }
}
