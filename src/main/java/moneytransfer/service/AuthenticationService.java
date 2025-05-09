package moneytransfer.service;

import moneytransfer.dto.CredentialsDTO;
import moneytransfer.repository.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session);
    ResponseEntity<String> logout(HttpSession session);
    ResponseEntity<String> registerUser(UserEntity user);
}
