package io.datajek.moneytransferrest.controller;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

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
