package io.datajek.moneytransferrest;

import io.datajek.moneytransferrest.BankUser;
import io.datajek.moneytransferrest.BankUserNotFoundException;
import io.datajek.moneytransferrest.BankUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BankUserService {
    @Autowired
    BankUserRepository repo;

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
        Optional<BankUser> User = repo.findById(id);

        if (User.isPresent()){
            UserPatch.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(BankUser.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, User.get(), value);
            });
        } else
            throw new BankUserNotFoundException("User with id {"+ id +"} not found");

        return repo.save(User.get());
    }


    public void deleteUser(int id) {
        Optional<BankUser> tempUser = repo.findById(id);

        if(tempUser.isEmpty())
            throw new BankUserNotFoundException("User with id {" + id + "} not found");

        repo.delete(tempUser.get());
    }
}