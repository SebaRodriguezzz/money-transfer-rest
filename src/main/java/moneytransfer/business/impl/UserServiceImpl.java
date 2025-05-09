package moneytransfer.business.impl;

import moneytransfer.business.TransactionService;
import moneytransfer.business.UserService;
import moneytransfer.exception.transaction.SameAccountTransactionException;
import moneytransfer.exception.user.InsufficientFundsException;
import moneytransfer.exception.user.UserNotFoundException;
import moneytransfer.persistence.UserPersistence;
import moneytransfer.persistence.entity.TransactionEntity;
import moneytransfer.persistence.entity.UserEntity;
import moneytransfer.web.dto.TransactionDTO;
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
        boolean sufficientFunds = user.getBalance().compareTo(amount) > 0;
        if (!sufficientFunds) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }

    private UserEntity findByAccountNumber(Long accountNumber) {
        return userPersistence.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new UserNotFoundException("User with account number {" + accountNumber + "} not found"));
    }

}
