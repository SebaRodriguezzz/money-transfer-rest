package io.datajek.moneytransferrest.controller;

import io.datajek.moneytransferrest.dto.CredentialsDTO;
import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.service.UserMapper;
import io.datajek.moneytransferrest.service.UserService;
import io.datajek.moneytransferrest.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferDTO> transferMoney(@RequestBody TransferDTO transfer, HttpSession session) {
        long receiverAccountNumber = transfer.getReceiverAccountNumber();
        BigDecimal amount = transfer.getAmount();
        TransferDTO transferDTO = userService.transferMoney(receiverAccountNumber, amount, (String) session.getAttribute("loggedInUser"));
        return ResponseEntity.ok(transferDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDTO credentials, HttpSession session) {
        ResponseEntity<String> isAuthenticated = userService.authenticate(credentials.getUsername(), credentials.getPassword(), session);
        return ResponseEntity.ok(isAuthenticated.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable int id) {
        UserEntity userEntity = userService.findById(id);
        UserDTO userDTO = userMapper.toUserDTO(userEntity);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserEntity> userEntities = userService.findAll();
        List<UserDTO> userDTOs = userMapper.toUserDTOList(userEntities);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.save(userMapper.toUserEntity(userDTO));
        UserDTO newUserDTO = userMapper.toUserDTO(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserEntity updatedUserEntity = userService.update(id, userMapper.toUserEntity(userDTO));
        UserDTO updatedUserDTO = userMapper.toUserDTO(updatedUserEntity);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
