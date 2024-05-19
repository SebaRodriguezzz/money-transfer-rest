package io.datajek.moneytransferrest.web.in;

import io.datajek.moneytransferrest.web.api.UserControllerAPI;
import io.datajek.moneytransferrest.web.dto.TransactionDTO;
import io.datajek.moneytransferrest.web.dto.UserDTO;
import io.datajek.moneytransferrest.persistence.entity.TransactionEntity;
import io.datajek.moneytransferrest.persistence.entity.UserEntity;
import io.datajek.moneytransferrest.business.TransactionService;
import io.datajek.moneytransferrest.web.mapper.TransactionMapper;
import io.datajek.moneytransferrest.web.mapper.UserMapper;
import io.datajek.moneytransferrest.business.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerAPI {

    private final UserService userService;
    private final TransactionService transactionService;
    private final UserMapper userMapper;
    private final TransactionMapper transactionMapper;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@RequestBody TransactionDTO transactionDTO, HttpSession session) {
        return ResponseEntity.ok(
                transactionMapper.toTransactionDTO(
                        userService.transferMoney(transactionDTO, (UserEntity) session.getAttribute("loggedInUser"))
                )
        );
    }

    @GetMapping("/transactions") // /users/transactions?type=sent or /users/transactions?type=received
    public ResponseEntity<List<TransactionDTO>> getTransactions(HttpSession session, @RequestParam(required = false) String type) {
        List<TransactionEntity> transactions = transactionService.findByType((UserEntity) session.getAttribute("loggedInUser"), type);
        return ResponseEntity.ok(transactionMapper.toTransactionDTOList(transactions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
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
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                userMapper.toUserDTO(userService.update(id, userMapper.toUserEntity(userDTO)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
