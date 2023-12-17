package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    TransferDTO transferMoney(long userId, BigDecimal amount, String senderUsername);
    ResponseEntity<String> authenticate(String username, String password, HttpSession session);
    UserEntity findById(int id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity p);
    UserEntity update(int id, UserEntity p);
    void delete(int id);
}
