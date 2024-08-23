package io.datajek.moneytransferrest.persistence.repository;

import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCredentialsUsername(String senderUsername);
    Optional<UserEntity> findByAccountNumber(Long accountNumber);

}
