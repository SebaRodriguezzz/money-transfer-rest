package io.datajek.moneytransferrest.repository;

import io.datajek.moneytransferrest.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {
    Optional<UserCredentials> findByUsername(String username);
}
