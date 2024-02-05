package io.datajek.moneytransferrest.persistence.impl;

import io.datajek.moneytransferrest.persistence.UserCredentialsPersistence;
import io.datajek.moneytransferrest.persistence.entity.UserCredentialsEntity;
import io.datajek.moneytransferrest.persistence.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserCredentialsPersistenceImpl implements UserCredentialsPersistence {

    private final UserCredentialsRepository userCredentialsRepository;

    public Optional<UserCredentialsEntity> findByUsername(String username) {
        return userCredentialsRepository.findByUsername(username);
    }
}
