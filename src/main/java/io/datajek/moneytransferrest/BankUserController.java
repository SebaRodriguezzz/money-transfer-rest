package io.datajek.moneytransferrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class BankUserController {

    @Autowired
    BankUserService service;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/{id}")
    public BankUser getUser(@PathVariable int id){
        return service.getUser(id);
    }

    @GetMapping
    public List<BankUser> players(){
        return service.getAllUsers();
    }

    @PostMapping
    public BankUser addUser(@RequestBody BankUser p) {
        return service.addUser(p);
    }

    @PutMapping("/{id}")
    public BankUser updateUser(@PathVariable int id, @RequestBody BankUser p) {
        return service.updateUser(id, p);
    }

    @PatchMapping("/{id}")
    public BankUser partialUpdate(@PathVariable int id, @RequestBody Map<String, Object> UserPatch){
        return service.patch(id, UserPatch);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }
}
