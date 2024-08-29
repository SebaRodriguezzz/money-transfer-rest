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
    }



    @Test
    public void findByType(){
        //GIVEN: A user and a transaction to be retrieved
        UserEntity user = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity user2 = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        TransactionEntity transaction = new TransactionEntity(Instant.now(), user, user2, new BigDecimal("500.00"));
        String type = "sent";

        when(transactionRepository.findBySenderAccountNumber(user.getAccountNumber())).thenReturn(List.of(transaction));
        //WHEN: Retrieving transactions by type
        List<TransactionEntity> sentTransactions = transactionService.findByType(user, type);
        //THEN: Verify the transactions are as expected
        assertEquals(sentTransactions, List.of(transaction));
    }

}