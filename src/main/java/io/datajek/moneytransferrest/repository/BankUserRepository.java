package io.datajek.moneytransferrest.repository;

import io.datajek.moneytransferrest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankUserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByCredentialsUsername(String senderUsername);
}
