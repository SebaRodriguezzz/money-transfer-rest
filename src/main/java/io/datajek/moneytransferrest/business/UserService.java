package io.datajek.moneytransferrest.business;


import io.datajek.moneytransferrest.web.dto.TransactionDTO;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import java.util.List;

public interface UserService {
    TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender);
    UserEntity findById(Long id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity p);
    UserEntity update(Long id, UserEntity p);
    void delete(Long id);
}
