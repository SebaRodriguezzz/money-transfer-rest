package io.datajek.moneytransferrest.repository;

import io.datajek.moneytransferrest.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findBySenderAccountNumber(Long accountNumber);
    List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber);

}