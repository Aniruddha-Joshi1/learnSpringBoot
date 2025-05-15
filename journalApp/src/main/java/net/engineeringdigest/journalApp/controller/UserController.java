package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/allUsers")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> resp = new ApiResponse<>();
        resp.setData(userService.getAllUsers());
        resp.setMessage("Fetched all the journal entries");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody User user){
        ApiResponse<User> resp = new ApiResponse<>();
        userService.saveUser(user);
        resp.setData(user);
        resp.setMessage("Entry added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long myId){
        ApiResponse<User> resp = new ApiResponse<>();
        User neededEntry = userService.getUserById(myId);
        resp.setData(neededEntry);
        resp.setMessage(String.format("User with id %d retrieved successfully", myId));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable Long myId){
        ApiResponse<String> resp = new ApiResponse<>();
        userService.deleteUserById(myId);
        resp.setMessage(String.format("User with id %d, deleted successfully", myId));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody User user, @PathVariable long id){
        ApiResponse<User> resp = new ApiResponse<>();
        User userExists = userService.getByUserName(user.getUsername());
        if(userExists != null) {
            resp.setMessage("Username already exists, choose some other username");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        }
        User userInDb = userService.getUserById(id);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
            resp.setMessage("User updated successfully");
            resp.setData(userInDb);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        resp.setMessage("User not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

//    @PutMapping("/id/{myId}")
//    public ResponseEntity<ApiResponse<JournalEntry>> updateEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
//        ApiResponse<JournalEntry> resp = new ApiResponse<>();
//        JournalEntry updatedJournalEntry = journalEntryService.updateJournalEntry(myId, myEntry);
//        if(updatedJournalEntry == null){
//            resp.setMessage("This updation is not possible since the entry does not exist");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
//        } else{
//            resp.setMessage(String.format("Updated the journal entry with title - {}, successfully", updatedJournalEntry.getTitle()));
//            resp.setData(updatedJournalEntry);
//            return ResponseEntity.status(HttpStatus.OK).body(resp);
//        }
//    }
}
