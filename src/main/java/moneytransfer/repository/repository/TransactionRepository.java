package moneytransfer.repository.repository;

import moneytransfer.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findBySenderAccountNumber(Long accountNumber);
    List<TransactionEntity> findByReceiverAccountNumber(Long accountNumber);
}