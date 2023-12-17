package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.exception.SameAccountTransferException;
import io.datajek.moneytransferrest.exception.TransactionFailedException;
import io.datajek.moneytransferrest.exception.UserNotFoundException;
import io.datajek.moneytransferrest.exception.InsufficientFundsException;
import io.datajek.moneytransferrest.model.UserCredentials;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import io.datajek.moneytransferrest.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository repo;

    @Autowired
    UserCredentialsRepository credentialsRepository;

    @Autowired
    TransferService transferService;

    public TransferDTO transferMoney(long receiverAccountNumber, BigDecimal amount, String senderUsername) {
        UserEntity receiver = findByAccountNumber(receiverAccountNumber);
        UserEntity sender = getSenderByUsername(senderUsername);
        if (sender.equals(receiver)) {
            throw new SameAccountTransferException("Sender and receiver accounts are the same");
        }
        failIfInsufficientFunds(sender, amount);
        transferService.performTransaction(receiver, sender, amount);
        return createTransferDTO(sender, receiver, amount);
    }

    private UserEntity findByAccountNumber(long receiverAccountNumber) {
        return repo.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new UserNotFoundException("User with account number {" + receiverAccountNumber + "} not found"));
    }

    private UserEntity getSenderByUsername(String senderUsername) {
        return repo.findByCredentialsUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username {" + senderUsername + "} not found"));
    }

    private void failIfInsufficientFunds(UserEntity sender, BigDecimal amount) {
        BigDecimal senderBalance = sender.getBalance();
        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }

    private TransferDTO createTransferDTO(UserEntity sender, UserEntity user, BigDecimal amount) {
        return new TransferDTO(Instant.now(), sender.getAccountNumber(), user.getAccountNumber(), amount);
    }

    public boolean authenticate(String username, String password) {
        Optional<UserCredentials> userCredentials = credentialsRepository.findByUsername(username);
        return userCredentials.isPresent() && userCredentials.get().getPassword().equals(password);
    }

    public UserEntity findById(int id) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        return tempUser.get();
    }

    public List<UserEntity> findAll(){
        return repo.findAll();
    }

    public UserEntity save(UserEntity p){
        return repo.save(p);
    }

    public UserEntity update(int id, UserEntity p) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        p.setId(id);
        return repo.save(p);
    }

    public void delete(int id) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        repo.delete(tempUser.get());
    }
}
