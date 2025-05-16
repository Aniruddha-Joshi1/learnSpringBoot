package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.response.ApiResponse;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public")
@RestController
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody User user){
        ApiResponse<User> resp = new ApiResponse<>();
        userService.savePasswordEncrypted(user);
        resp.setData(user);
        resp.setMessage("Entry added successfully");
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
