package io.datajek.moneytransferrest.service;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.exception.user.UserAlreadyRegisteredException;
import io.datajek.moneytransferrest.model.UserCredentialsEntity;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.repository.UserCredentialsRepository;
import io.datajek.moneytransferrest.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository credentialsRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, UserCredentialsRepository credentialsRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> authenticate(CredentialsDTO credentials, HttpSession session) {
        Optional<UserCredentialsEntity> userCredentials = credentialsRepository.findByUsername(credentials.getUsername());

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
        if (credentialsRepository.findByUsername(user.getCredentials().getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException("User with username {" + user.getCredentials().getUsername() + "} already registered");
        }
        user.getCredentials().setPassword(passwordEncoder.encode(user.getCredentials().getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
