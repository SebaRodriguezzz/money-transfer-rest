package moneytransfer.persistence.repository;

import moneytransfer.persistence.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long> {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
