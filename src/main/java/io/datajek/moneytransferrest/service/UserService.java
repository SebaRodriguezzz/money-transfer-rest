package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender);
    ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session);
    UserEntity findById(int id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity p);
    UserEntity update(int id, UserEntity p);
    void delete(int id);
}
