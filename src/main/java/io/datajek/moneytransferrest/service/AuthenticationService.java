package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session);
    ResponseEntity<String> logout(HttpSession session);
    ResponseEntity<String> registerUser(UserEntity user);
}
