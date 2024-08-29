package io.datajek.moneytransferrest.business;

import io.datajek.moneytransferrest.business.impl.UserServiceImpl;
import io.datajek.moneytransferrest.exception.transaction.SameAccountTransactionException;
import io.datajek.moneytransferrest.exception.user.InsufficientFundsException;
import io.datajek.moneytransferrest.exception.user.UserNotFoundException;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.web.dto.TransactionDTO;
import org.apache.catalina.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserPersistence userPersistence;
    @Mock
    TransactionService transactionService;
    @InjectMocks
    UserServiceImpl userService;

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
    public void transferMoneySuccessful() {
        final BigDecimal amount = new BigDecimal("500.00");
        //GIVEN: Valid users for the transaction
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        UserEntity receiver = createMockedResponse(2L, 5678, new BigDecimal("1000.00"));
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setSenderAccountNumber(1234);
        transactionDTO.setReceiverAccountNumber(5678);
        transactionDTO.setAmount(amount);

        when(userPersistence.findByAccountNumber(5678)).thenReturn(Optional.of(receiver));
        TransactionEntity expectedTransaction = new TransactionEntity(Instant.now(), sender, receiver, transactionDTO.getAmount());
        when(transactionService.performTransaction(receiver, sender,transactionDTO.getAmount()))
                .thenReturn(expectedTransaction);
        //WHEN: Calling transferMoney
        TransactionEntity actualTransaction = userService.transferMoney(transactionDTO, sender);
        //THEN: The transaction should be successful
        assertNotNull(actualTransaction);
        assertEquals(expectedTransaction, actualTransaction);
        //AND: The repository should have been called once
        verify(userPersistence, times(1)).findByAccountNumber(5678);
        verify(userPersistence, times(1)).findByAccountNumber(5678);
    }

    @Test
    public void transferMoneyThrowsExceptionWhenSenderAndReceiverAreTheSame() {
        // GIVEN: A transaction with the same sender and receiver
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber(1234);
        transactionDTO.setReceiverAccountNumber(1234);

        // WHEN: Calling transferMoney
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        when(userPersistence.findByAccountNumber(1234)).thenReturn(Optional.of(sender));

        // THEN: A SameAccountTransactionException should be thrown
        assertThrows(SameAccountTransactionException.class, () -> userService.transferMoney(transactionDTO, sender));
    }

    @Test
    public void transferMoneyThrowsExceptionWhenInsufficientFunds() {
        // GIVEN: A transaction with insufficient funds
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber(1234);
        transactionDTO.setReceiverAccountNumber(5678);
        transactionDTO.setAmount(new BigDecimal("5000.00"));
        // GIVEN: A sender with a balance less than the transaction amount
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));

        UserEntity receiver = createMockedResponse(2L, 5678, new BigDecimal("1000.00"));
        when(userPersistence.findByAccountNumber(5678)).thenReturn(Optional.of(receiver));

        // WHEN: Calling transferMoney
        // THEN: An InsufficientFundsException should be thrown
        assertThrows(InsufficientFundsException.class, () -> userService.transferMoney(transactionDTO, sender));
    }

    @Test
    public void transferMoneyThrowsExceptionWhenReceiverDoesNotExist() {
        // GIVEN: A transaction with a non-existent receiver
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSenderAccountNumber(1234);
        transactionDTO.setReceiverAccountNumber(5678);
        transactionDTO.setAmount(new BigDecimal("5000.00"));
        // GIVEN: A sender with sufficient funds to complete the transaction
        UserEntity sender = createMockedResponse(1L, 1234, new BigDecimal("3000.00"));
        when(userPersistence.findByAccountNumber(5678)).thenReturn(Optional.empty());
        // WHEN: Calling transferMoney
        // THEN: A UserNotFoundException should be thrown
        assertThrows(UserNotFoundException.class, () -> userService.transferMoney(transactionDTO, sender));
    }

    @Test
    public void findByIdThrowsExceptionWhenUserDoesNotExist() {
        //GIVEN: An ID that does not exist and an empty response
        Long id = 123L;
        when(userPersistence.findById(id)).thenReturn(Optional.empty());
        //WHEN: Calling the findById service method
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
        //THEN: An UserNotFoundException should be thrown
    }

    @Test
    public void findById() {
        // GIVEN: An existing ID and a successful response
        Long existingId = 123L;
        UserEntity mockedResponse = createMockedResponse(existingId);

        when(userPersistence.findById(existingId)).thenReturn(Optional.of(mockedResponse));
        // WHEN: Calling the findById service method
        UserEntity actualResponse = userService.findById(existingId);
        // THEN: Response should not be null and should contain the expected id
        assertNotNull(actualResponse);
        assertEquals(existingId, actualResponse.getId());
        // AND: User should be returned and repository should have been called at least once
        verify(userPersistence, atLeastOnce()).findById(existingId);
    }

    @Test
    public void findAll() {
        // GIVEN: A list of users
        UserEntity user1 = createMockedResponse(1L);
        UserEntity user2 = createMockedResponse(2L);
        when(userPersistence.findAll()).thenReturn(List.of(user1, user2));
        // WHEN: Calling the findAll service method
        List<UserEntity> actualResponse = userService.findAll();
        // THEN: Response should not be null and should contain the expected users
        assertNotNull(actualResponse);
        assertEquals(2, actualResponse.size());
        assertEquals(user1, actualResponse.get(0));
        assertEquals(user2, actualResponse.get(1));
        // AND: Users should be returned and repository should have been called at least once
        verify(userPersistence, atLeastOnce()).findAll();
    }

    @Test
    public void save() {
        // GIVEN: A user to save
        UserEntity user = createMockedResponse(1L);
        when(userPersistence.save(user)).thenReturn(user);
        // WHEN: Calling the save service method
        UserEntity actualResponse = userService.save(user);
        // THEN: Response should not be null and should contain the expected user
        assertNotNull(actualResponse);
        assertEquals(user, actualResponse);
        // AND: User should be saved and repository should have been called at least once
        verify(userPersistence, atLeastOnce()).save(user);
    }

    @Test
    public void update() {
        // GIVEN: An existing ID and a user to update
        Long existingId = 123L;
        UserEntity user = createMockedResponse(existingId);
        when(userPersistence.findById(existingId)).thenReturn(Optional.of(user));
        when(userPersistence.save(user)).thenReturn(user);
        // WHEN: Calling the update service method
        UserEntity actualResponse = userService.update(existingId, user);
        // THEN: Response should not be null and should contain the expected user
        assertNotNull(actualResponse);
        assertEquals(user, actualResponse);
        // AND: User should be updated and repository should have been called at least once
        verify(userPersistence, atLeastOnce()).save(user);
    }

    @Test
    public void delete() {
        // GIVEN: An existing ID
        Long existingId = 123L;
        UserEntity existingUser = createMockedResponse(existingId);

        when(userPersistence.findById(existingId)).thenReturn(Optional.of(existingUser));
        // WHEN: Calling the delete service method
        userService.delete(existingId);
        // THEN: The repository's deleteById method should have been called with the correct ID
        verify(userPersistence, atLeastOnce()).deleteById(existingId);
    }

}