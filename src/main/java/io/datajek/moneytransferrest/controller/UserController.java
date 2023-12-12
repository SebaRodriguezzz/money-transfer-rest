package io.datajek.moneytransferrest.controller;

import io.datajek.moneytransferrest.dto.TransferDTO;
import io.datajek.moneytransferrest.dto.UserDTO;
import io.datajek.moneytransferrest.model.UserEntity;
import io.datajek.moneytransferrest.service.UserMapper;
import io.datajek.moneytransferrest.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }
    
    @PostMapping("/transfer/{id}/{amount}")
    public ResponseEntity<TransferDTO> transferMoney(@PathVariable int id, @PathVariable BigDecimal amount, HttpSession session) {
        TransferDTO transferDTO = userService.transferMoney(id, amount, (String) session.getAttribute("loggedInUser"));
        return ResponseEntity.ok(transferDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            session.setAttribute("loggedInUser", username);
            return ResponseEntity.ok("Successful login. User: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        UserEntity userEntity = userService.getUser(id);
        UserDTO userDTO = userMapper.toUserDTO(userEntity);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> users() {
        List<UserEntity> userEntities = userService.getAllUsers();
        List<UserDTO> userDTOs = userMapper.toUserDTOList(userEntities);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.addUser(userMapper.toUserEntity(userDTO));
        UserDTO newUserDTO = userMapper.toUserDTO(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserEntity updatedUserEntity = userService.updateUser(id, userMapper.toUserEntity(userDTO));
        UserDTO updatedUserDTO = userMapper.toUserDTO(updatedUserEntity);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdate(@PathVariable int id, @RequestBody Map<String, Object> userPatch) {
        UserEntity patchedUserEntity = userService.patch(id, userPatch);
        UserDTO patchedUserDTO = userMapper.toUserDTO(patchedUserEntity);
        return ResponseEntity.ok(patchedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
