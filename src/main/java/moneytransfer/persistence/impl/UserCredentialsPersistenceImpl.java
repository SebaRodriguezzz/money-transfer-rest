package moneytransfer.persistence.impl;

import moneytransfer.persistence.UserCredentialsPersistence;
import moneytransfer.persistence.entity.UserCredentialsEntity;
import moneytransfer.persistence.repository.UserCredentialsRepository;
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
