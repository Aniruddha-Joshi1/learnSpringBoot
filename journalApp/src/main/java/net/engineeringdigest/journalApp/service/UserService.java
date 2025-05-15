package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        userRepositoryInterface.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        userRepositoryInterface.save(user);
    }

    public void deleteByUsername(String username){
        userRepositoryInterface.deleteByUsername(username);
    }

    public void deleteUserById(long id){
        userRepositoryInterface.deleteById(id);
    }

    public User getUserById(long id){
        return userRepositoryInterface.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepositoryInterface.findAll();
    }

    public User getByUserName(String userName){
        return userRepositoryInterface.findByUsername(userName);
    }
}
