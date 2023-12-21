package io.datajek.moneytransferrest;


import io.datajek.moneytransferrest.model.UserCredentialsEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/*
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
public class DataInitializer {
    private final AuthenticationService authenticationService;

    public DataInitializer(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initData() {
        UserCredentialsEntity userCredentials = new UserCredentialsEntity("john_doe", "john321");
        UserCredentialsEntity userCredentials2 = new UserCredentialsEntity("juana_morgan", "juana123");
        UserEntity user = new UserEntity("John Doe", "USA", new Date(), new BigDecimal("3000.00"), 123456789, userCredentials);
        UserEntity user2 = new UserEntity("Juana Morgan", "Argentina", new Date(), new BigDecimal("2300.00"), 987654321, userCredentials2);
        authenticationService.registerUser(user);
        authenticationService.registerUser(user2);
    }
}
