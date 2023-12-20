package io.datajek.moneytransferrest.service;


import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.model.TransactionEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import java.util.List;

public interface UserService {
    TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender);
    UserEntity findById(long id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity p);
    UserEntity update(long id, UserEntity p);
    void delete(long id);
}
