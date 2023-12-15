package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    TransferDTO transferMoney(int userId, BigDecimal amount, String senderUsername);
    boolean authenticate(String username, String password);
    UserEntity getUser(int id);
    List<UserEntity> getAllUsers();
    UserEntity addUser(UserEntity p);
    UserEntity updateUser(int id, UserEntity p);
    UserEntity patch(int id, Map<String, Object> UserPatch);
    void deleteUser(int id);
}
