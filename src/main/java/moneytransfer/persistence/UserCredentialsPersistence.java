package moneytransfer.persistence;

import moneytransfer.persistence.entity.UserCredentialsEntity;

import java.util.Optional;

public interface UserCredentialsPersistence {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
