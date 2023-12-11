package io.datajek.moneytransferrest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankUserRepository extends JpaRepository<BankUser, Integer> {

    Optional<BankUser> findByCredentialsUsername(String senderUsername);
}
