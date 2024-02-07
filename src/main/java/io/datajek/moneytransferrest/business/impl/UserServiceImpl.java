package io.datajek.moneytransferrest.business.impl;

import io.datajek.moneytransferrest.business.TransactionService;
import io.datajek.moneytransferrest.business.UserService;
import io.datajek.moneytransferrest.exception.transaction.SameAccountTransactionException;
import io.datajek.moneytransferrest.exception.user.InsufficientFundsException;
import io.datajek.moneytransferrest.exception.user.UserNotFoundException;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.web.dto.TransactionDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserPersistence userPersistence;
    private final TransactionService transactionService;


    @Transactional
    public TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender) {
        UserEntity receiver = findByAccountNumber(transaction.getReceiverAccountNumber());
        if (sender.getId().equals(receiver.getId())) {
            throw new SameAccountTransactionException("Sender and receiver accounts are the same");
        }
        failIfInsufficientFunds(sender, transaction.getAmount());
        return transactionService.performTransaction(receiver, sender, transaction.getAmount());
    }

    public UserEntity findById(Long id) {
        return userPersistence.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public List<UserEntity> findAll(){
        return userPersistence.findAll();
    }

    public UserEntity save(UserEntity p){
        return userPersistence.save(p);
    }

    public UserEntity update(Long id, UserEntity p) {
        p.setId(id);
        return userPersistence.findById(id)
                .map(userPersistence::save)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public void delete(Long id) {
        userPersistence.deleteById(id);
    }

    private void failIfInsufficientFunds(UserEntity user, BigDecimal amount) {
        if (user.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }

    private UserEntity findByAccountNumber(Long accountNumber) {
        return userPersistence.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new UserNotFoundException("User with account number {" + accountNumber + "} not found"));
    }

    private UserEntity findByUsername(String username) {
        return userPersistence.findByCredentialsUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username {" + username + "} not found"));
    }

}
