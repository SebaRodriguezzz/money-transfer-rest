package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.exception.TransactionFailedException;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    @Autowired
    UserRepository userRepo;

    public void performTransaction(UserEntity user, UserEntity sender, BigDecimal amount) {
        try {
            user.addBalance(amount);
            sender.subtractBalance(amount);
            userRepo.save(user);
            userRepo.save(sender);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed: " + e.getMessage());
        }
    }
}
