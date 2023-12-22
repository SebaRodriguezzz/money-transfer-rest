package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.exception.transaction.SameAccountTransactionException;
import io.datajek.moneytransferrest.exception.user.UserNotFoundException;
import io.datajek.moneytransferrest.exception.user.InsufficientFundsException;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender) {
        UserEntity receiver = findByAccountNumber(transaction.getReceiverAccountNumber());
        if (sender.getId() == receiver.getId()) {
            throw new SameAccountTransactionException("Sender and receiver accounts are the same");
        }
        failIfInsufficientFunds(sender, transaction.getAmount());
        return transactionService.performTransaction(receiver, sender, transaction.getAmount());
    }

    private UserEntity findByAccountNumber(Long accountNumber) {
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

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity p){
        return userRepository.save(p);
    }

    public UserEntity update(Long id, UserEntity p) {
        p.setId(id);
        return userRepository.findById(id)
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
