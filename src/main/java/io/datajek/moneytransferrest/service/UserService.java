package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.exception.TransactionFailedException;
import io.datajek.moneytransferrest.exception.UserNotFoundException;
import io.datajek.moneytransferrest.exception.InsufficientFundsException;
import io.datajek.moneytransferrest.model.UserCredentials;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.BankUserRepository;
import io.datajek.moneytransferrest.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


// BankUserService: agregar Impl
// aprender a usar optional
@Service
public class UserService {
    @Autowired
    BankUserRepository repo;

    // tiene q ser interfac.
    @Autowired
    private UserCredentialsRepository credentialsRepository;


    public TransferDTO transferMoney(int userId, BigDecimal amount, String senderUsername) {
        UserEntity user = getUserById(userId);
        UserEntity sender = getSenderByUsername(senderUsername);

        checkSufficientFunds(sender, amount);

        performMoneyTransfer(user, sender, amount);

        return createTransferDTO(sender, user, amount);
    }

    private UserEntity getUserById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id {" + id + "} not found"));
    }

    private UserEntity getSenderByUsername(String senderUsername) {
        return repo.findByCredentialsUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username {" + senderUsername + "} not found"));
    }

    private void checkSufficientFunds(UserEntity sender, BigDecimal amount) {
        BigDecimal senderBalance = sender.getBalance();
        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }

    private void performMoneyTransfer(UserEntity user, UserEntity sender, BigDecimal amount) {
        try {
            user.addBalance(amount);
            sender.subtractBalance(amount);
            repo.save(user);
            repo.save(sender);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction failed: " + e.getMessage());
        }
    }

    private TransferDTO createTransferDTO(UserEntity sender, UserEntity user, BigDecimal amount) {
        return new TransferDTO(Instant.now(), sender.getName(), user.getName(), amount);
    }

    public boolean authenticate(String username, String password) {
        Optional<UserCredentials> userCredentials = credentialsRepository.findByUsername(username);
        return userCredentials.isPresent() && userCredentials.get().getPassword().equals(password);
    }

    public UserEntity getUser(int id) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        return tempUser.get();
    }

    public List<UserEntity> getAllUsers(){
        return repo.findAll();
    }

    public UserEntity addUser(UserEntity p){
        return repo.save(p);
    }

    public UserEntity updateUser(int id, UserEntity p) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        p.setId(id);
        return repo.save(p);
    }

    public UserEntity patch(int id, Map<String, Object> UserPatch){
        Optional<UserEntity> user = repo.findById(id);

        if (user.isPresent()){
            UserPatch.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(UserEntity.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, user.get(), value);
            });
        } else
            throw new UserNotFoundException("User with id {"+ id +"} not found");

        return repo.save(user.get());
    }

    public void deleteUser(int id) {
        Optional<UserEntity> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        repo.delete(tempUser.get());
    }
}
