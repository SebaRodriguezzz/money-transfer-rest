package moneytransfer.persistence.config;


import moneytransfer.model.NationalityEnum;
import moneytransfer.persistence.entity.UserCredentialsEntity;
import moneytransfer.persistence.entity.UserEntity;
import moneytransfer.business.AuthenticationService;
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
public class DataInitializerConfig {
    private final AuthenticationService authenticationService;

    public DataInitializerConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initData() {
        UserCredentialsEntity userCredentials = new UserCredentialsEntity("john_doe", "john321");
        UserCredentialsEntity userCredentials2 = new UserCredentialsEntity("juana_morgan", "juana123");
        UserEntity user = new UserEntity("John Doe", NationalityEnum.UNITED_STATES, new Date(), new BigDecimal("3000.00"), 123456789, userCredentials);
        UserEntity user2 = new UserEntity("Juana Morgan", NationalityEnum.ARGENTINA, new Date(), new BigDecimal("2300.00"), 987654321, userCredentials2);
        authenticationService.registerUser(user);
        authenticationService.registerUser(user2);
    }
}
