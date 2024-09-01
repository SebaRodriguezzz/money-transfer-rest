package moneytransfer.business;

import moneytransfer.web.dto.CredentialsDTO;
import moneytransfer.persistence.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session);
    ResponseEntity<String> logout(HttpSession session);
    ResponseEntity<String> registerUser(UserEntity user);
}
