package moneytransfer.service;

import moneytransfer.service.impl.AuthenticationServiceImpl;
import moneytransfer.exception.user.UserAlreadyRegisteredException;
import moneytransfer.repository.UserCredentialsPersistence;
import moneytransfer.repository.UserPersistence;
import moneytransfer.repository.entity.UserCredentialsEntity;
import moneytransfer.repository.entity.UserEntity;
import moneytransfer.dto.CredentialsDTO;
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
        //GIVEN: Valid credentials for login
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

    @Test
    public void logout(){
        //GIVEN: A session to logout
        HttpSession session = mock(HttpSession.class);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Successful logout");
        //WHEN: Logging out
        ResponseEntity<String> response = authenticationService.logout(session);
        //THEN: User should be logged out
        assertEquals(expectedResponse, response);
        verify(session, atLeastOnce()).invalidate();
    }

    @Test
    public void registerUserSuccessful(){
        //GIVEN: Valid credentials for register
        UserEntity user = new UserEntity();
        UserCredentialsEntity credentials = new UserCredentialsEntity("user","pass");
        user.setCredentials(credentials);
        when(userPersistence.save(user)).thenReturn(user);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("User registered successfully");
        //WHEN: Calling register user method
        ResponseEntity<String> response = authenticationService.registerUser(user);
        //THEN: The user should be registered
        assertEquals(expectedResponse, response);
        verify(userPersistence, atLeastOnce()).save(user);
    }

    @Test
    public void registerUserAlreadyRegistered(){
        //GIVEN: An user for register
        UserEntity user = new UserEntity();
        UserCredentialsEntity credentials = new UserCredentialsEntity("user","pass");
        user.setCredentials(credentials);
        //WHEN: Verifying that the user is already registered
        when(credentialsPersistence.findByUsername(user.getCredentials().getUsername())).thenReturn(Optional.of(user.getCredentials()));
        //THEN: A UserAlreadyRegisteredException should be thrown
        assertThrows(UserAlreadyRegisteredException.class, () -> authenticationService.registerUser(user));
        verify(userPersistence, never()).save(any(UserEntity.class));
    }
}

