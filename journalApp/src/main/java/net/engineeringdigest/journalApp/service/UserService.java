package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    public void saveUser(User user) {
        userRepositoryInterface.save(user);
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

//    public User updateUser(long id, User user){
//        User oldEntry = userRepositoryInterface.findById(id).orElse(null);
//        if(oldEntry != null){
//            oldEntry.se(newJournalEntry.getContent());
//            oldEntry.setTitle(newJournalEntry.getTitle());
//            journalEntryRepositoryInterface.save(newJournalEntry);
//            return newJournalEntry;
//        }
//        return null;
//    }
}
