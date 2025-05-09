package moneytransfer.config;


import moneytransfer.model.NationalityEnum;
import moneytransfer.repository.entity.UserCredentialsEntity;
import moneytransfer.repository.entity.UserEntity;
import moneytransfer.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


/*
POST /register: Register new user account.
POST /login: Authenticate user credentials.
POST /users/transfer: Initiate money transfers between users.
GET /users/transactions: Retrieve transaction history for a user.
GET /users/transactions?type=sent: Retrieve sent transactions for a user.
GET /users/transactions?type=received: Retrieve received transactions for a user.
POST /logout: Log out from user account.

 JSON for transfer:
 {
  "receiverAccountNumber": 123456789,
  "amount": 3000
}

JSON for login:
{
  "username": "john_doe",
  "password": "john321"
}
*/


@Component
public class DataInitializerConfig {
    private final AuthenticationService authenticationService;

    public DataInitializerConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initData() {
        UserCredentialsEntity userCredentials = new UserCredentialsEntity("john_doe", "john321");
        UserCredentialsEntity userCredentials2 = new UserCredentialsEntity("juana_morgan", "juana123");
        UserEntity user = new UserEntity("John Doe", NationalityEnum.UNITED_STATES, new Date(), new BigDecimal("3000.00"), 123456789L, userCredentials);
        UserEntity user2 = new UserEntity("Juana Morgan", NationalityEnum.ARGENTINA, new Date(), new BigDecimal("2300.00"), 987654321L, userCredentials2);
        authenticationService.registerUser(user);
        authenticationService.registerUser(user2);
    }
}
