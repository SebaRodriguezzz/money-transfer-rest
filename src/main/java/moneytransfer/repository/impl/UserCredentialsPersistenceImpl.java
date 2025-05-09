package moneytransfer.repository.impl;

import moneytransfer.repository.UserCredentialsPersistence;
import moneytransfer.repository.entity.UserCredentialsEntity;
import moneytransfer.repository.repository.UserCredentialsRepository;
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
