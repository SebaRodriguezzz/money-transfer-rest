package io.datajek.moneytransferrest.persistence;

import io.datajek.moneytransferrest.persistence.entity.UserCredentialsEntity;

import java.util.Optional;

public interface UserCredentialsPersistence {
    Optional<UserCredentialsEntity> findByUsername(String username);
}
