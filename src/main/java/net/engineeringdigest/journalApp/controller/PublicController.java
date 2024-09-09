package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.journalEntry.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    public UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
