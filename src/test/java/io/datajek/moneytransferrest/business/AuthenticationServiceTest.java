package io.datajek.moneytransferrest.business;

import io.datajek.moneytransferrest.business.impl.AuthenticationServiceImpl;
import io.datajek.moneytransferrest.persistence.UserCredentialsPersistence;
import io.datajek.moneytransferrest.persistence.UserPersistence;
import io.datajek.moneytransferrest.persistence.entity.UserCredentialsEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.web.dto.CredentialsDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Enumeration;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserPersistence userPersistence;
    @Mock
    private UserCredentialsPersistence credentialsPersistence;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Before
    public void setUp() { MockitoAnnotations.openMocks(this); }


    @Test
    public void authenticateSuccessful(){
        //GIVEN: Valid credentials
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername("user");
        credentialsDTO.setPassword("pass");

        UserEntity user = new UserEntity();
        user.setName("name");
        UserCredentialsEntity credentials = new UserCredentialsEntity("user","pass");
        credentials.setUser(user);

        HttpSession session = mock(HttpSession.class);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Successful login. User: " + user.getName());

        when(credentialsPersistence.findByUsername(credentialsDTO.getUsername())).thenReturn(Optional.of(credentials));
        when(passwordEncoder.matches(credentialsDTO.getPassword(), credentials.getPassword())).thenReturn(true);
        //WHEN: Authenticating
        ResponseEntity<String> response = authenticationService.authenticate(credentialsDTO, session);
        //THEN: User should be logged in
        verify(session).setAttribute("loggedInUser", user);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void authenticationFailsInvalidCredentials() {
        //GIVEN: Invalid credentials
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername("user");
        credentialsDTO.setPassword("invalidPass");

        UserEntity user = new UserEntity();
        user.setName("name");
        UserCredentialsEntity validCredentials = new UserCredentialsEntity("user", "pass");
        validCredentials.setUser(user);

        HttpSession session = mock(HttpSession.class);
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        when(credentialsPersistence.findByUsername(credentialsDTO.getUsername())).thenReturn(Optional.of(validCredentials));
        when(passwordEncoder.matches(credentialsDTO.getPassword(), validCredentials.getPassword())).thenReturn(false);
        //WHEN: Authenticating
        ResponseEntity<String> response = authenticationService.authenticate(credentialsDTO, session);
        //THEN: User should be logged in
        assertEquals(expectedResponse, response);
        verify(session, never()).setAttribute(anyString(), any());
    }
}

