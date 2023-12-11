package io.datajek.moneytransferrest;

import io.datajek.moneytransferrest.BankUser;
import io.datajek.moneytransferrest.BankUserNotFoundException;
import io.datajek.moneytransferrest.BankUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BankUserService {
    @Autowired
    BankUserRepository repo;

    @Autowired
    private UserCredentialsRepository credentialsRepository;

    public BankUser transferMoney(int id, BigDecimal amount, String senderUsername){
        Optional<BankUser> user = repo.findById(id);
        Optional<BankUser> sender = repo.findByCredentialsUsername(senderUsername);

        if(user.isEmpty())
            throw new BankUserNotFoundException("User with id {" + id + "} not found");

        BankUser u =  user.get();
        BankUser s = sender.get();
        u.setBalance(u.getBalance().add(amount));
        s.setBalance(s.getBalance().subtract(amount));
        return repo.save(u);
    }

    public boolean authenticate(String username, String password) {
        Optional<UserCredentials> userCredentials = credentialsRepository.findByUsername(username);
        return userCredentials.isPresent() && userCredentials.get().getPassword().equals(password);
    }

    public BankUser getUser(int id) {
        Optional<BankUser> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new BankUserNotFoundException("User with id {" + id + "} not found");

        return tempUser.get();
    }

    public List<BankUser> getAllUsers(){
        return repo.findAll();
    }

    public BankUser addUser(BankUser p){
        return repo.save(p);
    }

    public BankUser updateUser(int id, BankUser p) {
        Optional<BankUser> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new BankUserNotFoundException("User with id {" + id + "} not found");

        p.setId(id);
        return repo.save(p);
    }

    public BankUser patch(int id, Map<String, Object> UserPatch){
        Optional<BankUser> user = repo.findById(id);

        if (user.isPresent()){
            UserPatch.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(BankUser.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, user.get(), value);
            });
        } else
            throw new BankUserNotFoundException("User with id {"+ id +"} not found");

        return repo.save(user.get());
    }

    public void deleteUser(int id) {
        Optional<BankUser> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new BankUserNotFoundException("User with id {" + id + "} not found");

        repo.delete(tempUser.get());
    }
}
