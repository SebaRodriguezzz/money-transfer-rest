package io.datajek.moneytransferrest.controller;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.dto.TransactionDTO;
import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.service.AuthenticationServiceImpl;
import io.datajek.moneytransferrest.service.mapper.TransactionMapper;
import io.datajek.moneytransferrest.service.mapper.UserMapper;
import io.datajek.moneytransferrest.service.UserService;
import io.datajek.moneytransferrest.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TransactionMapper transactionMapper;
    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper, TransactionMapper transactionMapper, AuthenticationServiceImpl authenticationService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.transactionMapper = transactionMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@RequestBody TransactionDTO transactionDTO, HttpSession session) {
        return ResponseEntity.ok(
                transactionMapper.toTransferDTO(
                        userService.transferMoney(transactionDTO, (UserEntity) session.getAttribute("loggedInUser"))
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDTO credentials, HttpSession session) {
        return authenticationService.authenticate(credentials, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        return authenticationService.logout(session);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable int id) {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userMapper.toUserDTOList(userService.findAll()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userMapper.toUserDTO(userService.save(userMapper.toUserEntity(userDTO)))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                userMapper.toUserDTO(userService.update(id, userMapper.toUserEntity(userDTO)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
