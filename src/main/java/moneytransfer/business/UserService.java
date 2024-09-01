package moneytransfer.business;


import moneytransfer.web.dto.TransactionDTO;
import moneytransfer.persistence.entity.TransactionEntity;
import moneytransfer.persistence.entity.UserEntity;
import java.util.List;

public interface UserService {
    TransactionEntity transferMoney(TransactionDTO transaction, UserEntity sender);
    UserEntity findById(Long id);
    List<UserEntity> findAll();
    UserEntity save(UserEntity p);
    UserEntity update(Long id, UserEntity p);
    void delete(Long id);
}
