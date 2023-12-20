package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.exception.SameAccountTransactionException;
import io.datajek.moneytransferrest.exception.UserNotFoundException;
import io.datajek.moneytransferrest.exception.InsufficientFundsException;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TransactionServiceImpl transactionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TransactionServiceImpl transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    public TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender) {
        UserEntity receiver = findByAccountNumber(transaction.getReceiverAccountNumber());
        if (sender.equals(receiver)) {
            throw new SameAccountTransactionException("Sender and receiver accounts are the same");
        }
        failIfInsufficientFunds(sender, transaction.getAmount());
        return transactionService.performTransaction(receiver, sender, transaction.getAmount());
    }

    private UserEntity findByAccountNumber(long accountNumber) {
        return userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new UserNotFoundException("User with account number {" + accountNumber + "} not found"));
    }

    private UserEntity findByUsername(String username) {
        return userRepository.findByCredentialsUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username {" + username + "} not found"));
    }

    private void failIfInsufficientFunds(UserEntity user, BigDecimal amount) {
        if (user.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }


    public UserEntity findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity p){
        return userRepository.save(p);
    }

    public UserEntity update(int id, UserEntity p) {
        p.setId(id);
        return userRepository.findById(id)
                .map(user -> userRepository.save(p))
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public void delete(int id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new UserNotFoundException("User with id {" + id + "} not found");
                });
    }
}
