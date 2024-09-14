package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.journalEntry.User;
import net.engineeringdigest.journalApp.services.UserDetailServiceImpl;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.utilities.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody User user){
        userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occur while createAuthenticationToken", e);
            return new ResponseEntity("Incorrect Username or Password",HttpStatus.UNAUTHORIZED);
        }
    }
}
