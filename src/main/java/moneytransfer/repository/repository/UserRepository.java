package moneytransfer.repository.repository;

import moneytransfer.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCredentialsUsername(String senderUsername);
    Optional<UserEntity> findByAccountNumber(Long accountNumber);

}
