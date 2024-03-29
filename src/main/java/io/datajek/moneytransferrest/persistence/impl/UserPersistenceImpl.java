package io.datajek.moneytransferrest.persistence.impl;

import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    public Optional<UserEntity> findByCredentialsUsername(String senderUsername) {
        return userRepository.findByCredentialsUsername(senderUsername);
    }

    public Optional<UserEntity> findByAccountNumber(Long accountNumber) {
        return userRepository.findByAccountNumber(accountNumber);
    }

    public UserEntity save(UserEntity p) {
        return userRepository.save(p);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
