package io.datajek.moneytransferrest.persistence;

import io.datajek.moneytransferrest.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserPersistence {

    Optional<UserEntity> findByCredentialsUsername(String senderUsername);
    Optional<UserEntity> findByAccountNumber(Long accountNumber);
    UserEntity save(UserEntity p);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAll();
    void deleteById(Long id);
}
