package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Authenticated endpoint
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserDetails")
    public ResponseEntity<ApiResponse<User>> getUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ApiResponse<User> resp = new ApiResponse<>();
        User neededEntry = userService.getByUserName(username);
        resp.setData(neededEntry);
        resp.setMessage(String.format("%s's data, retrieved successfully", username));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<ApiResponse<String>> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ApiResponse<String> resp = new ApiResponse<>();
        userService.deleteByUsername(username);
        resp.setMessage(String.format("%s deleted successfully", username));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ApiResponse<User> resp = new ApiResponse<>();
        User userInDb = userService.getByUserName(username);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.savePasswordEncrypted(userInDb);
            resp.setMessage("User updated successfully");
            resp.setData(userInDb);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        resp.setMessage("User not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }
}
