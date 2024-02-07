package io.datajek.moneytransferrest.business.impl;

import io.datajek.moneytransferrest.business.AuthenticationService;
import io.datajek.moneytransferrest.exception.user.UserAlreadyRegisteredException;
import io.datajek.moneytransferrest.persistence.UserCredentialsPersistence;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.UserCredentialsEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.web.dto.CredentialsDTO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserPersistence userPersistence;
    private final UserCredentialsPersistence credentialsPersistence;
    private final BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session) {
        Optional<UserCredentialsEntity> userCredentials = credentialsPersistence.findByUsername(credentials.getUsername());

        if (userCredentials.isPresent() && passwordEncoder.matches(credentials.getPassword(), userCredentials.get().getPassword())) {
            UserEntity user = userCredentials.get().getUser();
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok("Successful login. User: " + user.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Successful logout");
    }

    public ResponseEntity<String> registerUser(UserEntity user) {
        if (credentialsPersistence.findByUsername(user.getCredentials().getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException("User with username {" + user.getCredentials().getUsername() + "} already registered");
        }
        user.getCredentials().setPassword(passwordEncoder.encode(user.getCredentials().getPassword()));
        userPersistence.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
