package moneytransfer.repository;

import moneytransfer.repository.entity.UserCredentialsEntity;

import java.util.Optional;

public interface UserCredentialsPersistence {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
