package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.exception.SameAccountTransactionException;
import io.datajek.moneytransferrest.exception.UserNotFoundException;
import io.datajek.moneytransferrest.exception.InsufficientFundsException;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserCredentialsEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserRepository;
import io.datajek.moneytransferrest.repository.UserCredentialsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserCredentialsRepository credentialsRepository;
    private final TransactionService transactionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserCredentialsRepository credentialsRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.transactionService = transactionService;
    }


    public TransactionEntity transferMoney(long receiverAccountNumber, BigDecimal amount, UserEntity sender) {
        UserEntity receiver = findByAccountNumber(receiverAccountNumber);
        if (sender.equals(receiver)) {
            throw new SameAccountTransactionException("Sender and receiver accounts are the same");
        }
        failIfInsufficientFunds(sender, amount);

        //TODO: crear transfer model y devolverlo en vez de el DTO. el controller se encarga de convertirlo a DTO
        //TODO: preguntar a chatgpt como hacer que una secuencia de querys sean atomicas
        return transactionService.performTransaction(receiver, sender, amount);
    }

    private UserEntity findByAccountNumber(long receiverAccountNumber) {
        return userRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new UserNotFoundException("User with account number {" + receiverAccountNumber + "} not found"));
    }

    private UserEntity findByUsername(String senderUsername) {
        return userRepository.findByCredentialsUsername(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username {" + senderUsername + "} not found"));
    }

    private void failIfInsufficientFunds(UserEntity sender, BigDecimal amount) {
        BigDecimal senderBalance = sender.getBalance();
        if (senderBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }
    }

    //TODO: Separar las funcionalidades de LOGIN de usercontroller etc. hacer login y logout en un servicio aparte
    public ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session) {
        Optional<UserCredentialsEntity> userCredentials = credentialsRepository.findByUsername(credentials.getUsername());

        if (userCredentials.isPresent() && userCredentials.get().getPassword().equals(credentials.getPassword())) {
            UserEntity user = userCredentials.get().getUser();
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok("Successful login. User: " + user.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    public UserEntity findById(int id) {
        Optional<UserEntity> tempUser = userRepository.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        return tempUser.get();
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity p){
        return userRepository.save(p);
    }

    public UserEntity update(int id, UserEntity p) {
        Optional<UserEntity> tempUser = userRepository.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        p.setId(id);
        return userRepository.save(p);
    }

    public void delete(int id) {
        Optional<UserEntity> tempUser = userRepository.findById(id);

        if(tempUser.isEmpty())
            throw new UserNotFoundException("User with id {" + id + "} not found");

        userRepository.delete(tempUser.get());
    }
}
