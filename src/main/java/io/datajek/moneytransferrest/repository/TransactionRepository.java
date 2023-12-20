package io.datajek.moneytransferrest.repository;

import io.datajek.moneytransferrest.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}