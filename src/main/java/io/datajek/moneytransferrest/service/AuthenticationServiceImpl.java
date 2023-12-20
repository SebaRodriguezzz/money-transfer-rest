package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.model.UserCredentialsEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserCredentialsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserCredentialsRepository credentialsRepository;

    @Autowired
    public AuthenticationServiceImpl(UserCredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    public ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session) {
        Optional<UserCredentialsEntity> userCredentials = credentialsRepository.findByUsername(credentials.getUsername());

        if (userCredentials.isPresent() && userCredentials.get().getPassword().equals(credentials.getPassword())) {
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
}
