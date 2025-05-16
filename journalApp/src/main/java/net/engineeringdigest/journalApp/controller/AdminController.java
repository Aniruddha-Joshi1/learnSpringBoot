package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // Only admins can retrieve all the users. The first admin user must be manually entered in the DB
    @GetMapping("/allUsers")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        ApiResponse<List<User>> resp = new ApiResponse<>();
        if(!allUsers.isEmpty()){
            resp.setMessage("Retrieved all the users stored in DB successfully");
            resp.setData(allUsers);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } else{
            resp.setMessage("No Users stored in the DB");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping("/createAdminUser")
    public ResponseEntity<ApiResponse<User>> createAdminUser(@RequestBody User adminUser){
        ApiResponse<User> resp = new ApiResponse<>();
        User user = userService.createAdminUser(adminUser);
        resp.setData(user);
        resp.setMessage("Admin user created");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
