package io.datajek.moneytransferrest.business;

import io.datajek.moneytransferrest.business.impl.TransactionServiceImpl;
import io.datajek.moneytransferrest.exception.transaction.TransactionFailedException;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.persistence.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    UserPersistence userPersistence;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    TransactionServiceImpl transactionService;

    private UserEntity createMockedResponse(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        return user;
    }

    private UserEntity createMockedResponse(Long id, long accountNumber, BigDecimal balance) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setAccountNumber(accountNumber);
        user.setBalance(balance);
        return user;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void performTransactionSuccessful(){
        final BigDecimal amount = new BigDecimal("500.00");
        //GIVEN: Valid users for performing the transaction
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity receiver = createMockedResponse(2L, 5678, new BigDecimal("1000.00"));

        TransactionEntity expectedTransaction = new TransactionEntity(Instant.now(), sender, receiver, amount);
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(expectedTransaction);
        //WHEN: Performing the transaction
        TransactionEntity transaction = transactionService.performTransaction(receiver, sender, amount);
        //THEN: Verify the users are saved
        verify(userPersistence).save(sender);
        verify(userPersistence).save(receiver);
        //AND: Verify the transaction was saved
        verify(transactionRepository).save(any(TransactionEntity.class));
        //AND: Check if the balance was updated correctly
        assertEquals(new BigDecimal("2500.00"), sender.getBalance());
        assertEquals(new BigDecimal("1500.00"), receiver.getBalance());
        //AND: Check if the transaction is as expected
        assertEquals(transaction, expectedTransaction);
    }

    @Test
    public void performTransactionFails(){
        //GIVEN: Sender and amount but null receiver
        final BigDecimal amount = new BigDecimal("500.00");
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        //WHEN: Calling the method with invalid arguments
        //THEN: A TransactionFailedException should be thrown
        assertThrows(TransactionFailedException.class, ()-> transactionService.performTransaction(null, sender, amount));
        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    public void findByTypeSent(){
        //GIVEN: A user and a transaction to be retrieved
        UserEntity user = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity user2 = createMockedResponse(2L, 1234, new BigDecimal("3000.00"));
        TransactionEntity transaction = new TransactionEntity(Instant.now(), user, user2, new BigDecimal("500.00"));
        String type = "sent";

        when(transactionRepository.findBySenderAccountNumber(user.getAccountNumber())).thenReturn(List.of(transaction));
        //WHEN: Retrieving transactions by type
        List<TransactionEntity> sentTransactions = transactionService.findByType(user, type);
        //THEN: Verify the transactions are as expected
        assertEquals(sentTransactions, List.of(transaction));
    }

    @Test
    public void findByTypeReceived(){
        //GIVEN: A user and a transaction to be retrieved
        UserEntity user = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity user2 = createMockedResponse(2L, 1234, new BigDecimal("3000.00"));
        TransactionEntity transaction = new TransactionEntity(Instant.now(), user, user2, new BigDecimal("500.00"));
        String type = "received";

        when(transactionRepository.findByReceiverAccountNumber(user.getAccountNumber())).thenReturn(List.of(transaction));
        //WHEN: Retrieving transactions by type
        List<TransactionEntity> receivedTransactions = transactionService.findByType(user, type);
        //THEN: Verify the transactions are as expected
        assertEquals(receivedTransactions, List.of(transaction));
    }

    @Test
    public void findByTypeIsntValid(){
        //GIVEN: A user and a transaction to be retrieved
        UserEntity user = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity user2 = createMockedResponse(2L, 1234, new BigDecimal("3000.00"));
        TransactionEntity transactionSent = new TransactionEntity(Instant.now(), user, user2, new BigDecimal("500.00"));  // Sent transaction
        TransactionEntity transactionReceived = new TransactionEntity(Instant.now(), user2, user, new BigDecimal("300.00"));  // Received transaction
        String type = "invalid";

        when(transactionRepository.findBySenderAccountNumber(user.getAccountNumber())).thenReturn(List.of(transactionSent));
        when(transactionRepository.findByReceiverAccountNumber(user.getAccountNumber())).thenReturn(List.of(transactionReceived));
        //WHEN: Retrieving transactions by type
        List<TransactionEntity> transactions = transactionService.findByType(user, type);
        //THEN: Verify the transactions are as expected
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertEquals(transactionSent, transactions.get(0));
        assertEquals(transactionReceived, transactions.get(1));

    }

    @Test
    public void findById(){
        //GIVEN: A transaction to be retrieved
        TransactionEntity transaction = new TransactionEntity(Instant.now(), null, null, new BigDecimal("500.00"));
        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.of(transaction));
        //WHEN: Retrieving the transaction
        TransactionEntity actualTransaction = transactionService.findById(1L);
        //THEN: Verify the transaction is as expected
        assertEquals(transaction, actualTransaction);
    }

    @Test
    public void findAll(){
        //GIVEN: A list of transactions to be retrieved
        TransactionEntity transaction1 = new TransactionEntity(Instant.now(), null, null, new BigDecimal("500.00"));
        TransactionEntity transaction2 = new TransactionEntity(Instant.now(), null, null, new BigDecimal("500.00"));
        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));
        //WHEN: Retrieving all transactions
        List<TransactionEntity> transactions = transactionService.findAll();
        //THEN: Verify the transactions are as expected
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertEquals(transaction1, transactions.get(0));
        assertEquals(transaction2, transactions.get(1));
    }

    @Test
    public void delete(){
        //GIVEN: A transaction to be deleted
        TransactionEntity transaction = new TransactionEntity(Instant.now(), null, null, new BigDecimal("500.00"));
        //WHEN: Deleting the transaction
        transactionService.delete(1L);
        //THEN: Verify the transaction was deleted
        verify(transactionRepository).deleteById(1L);
    }
}
