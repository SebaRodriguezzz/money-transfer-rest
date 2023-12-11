package io.datajek.moneytransferrest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankUserRepository extends JpaRepository<BankUser, Integer> {

}
