package moneytransfer.web.in;

import moneytransfer.web.api.AuthenticationControllerAPI;
import moneytransfer.web.dto.CredentialsDTO;
import moneytransfer.persistence.entity.UserEntity;
import moneytransfer.business.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerAPI {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {
       return authenticationService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDTO credentials, HttpSession session) {
        return authenticationService.authenticate(credentials, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        return authenticationService.logout(session);
    }

}
