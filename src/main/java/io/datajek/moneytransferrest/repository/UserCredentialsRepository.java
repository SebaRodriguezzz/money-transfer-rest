package io.datajek.moneytransferrest.repository;

import io.datajek.moneytransferrest.model.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long> {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
