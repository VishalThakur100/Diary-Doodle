package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.journalEntry.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    AppCache appCache;

    //    here  ResponseEntity<?>    this ?  is called wild card
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();
        if(all!=null && all.size()>0){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
