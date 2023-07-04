package com.diagno.vision.webapps.diagnovision.controller;

import com.diagno.vision.webapps.diagnovision.dto.User;
import com.diagno.vision.webapps.diagnovision.helper.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserHelper userHelper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        boolean isUserCreated = userHelper.registerUser(user);

        if(isUserCreated)
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User registration failed! Please check the payload and try again!");
    }

    @GetMapping("/myAccount")
    public User editUser(Authentication authentication){
        return userHelper.fetchUser(authentication);
    }
}
