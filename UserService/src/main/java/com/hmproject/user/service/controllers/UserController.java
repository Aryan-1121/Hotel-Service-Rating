package com.hmproject.user.service.controllers;

import com.hmproject.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hmproject.user.service.entities.User;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    //save

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser= userService.saveUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User userFromDb= userService.getUser(userId);
        return ResponseEntity.ok(userFromDb);

    }

    // get all user

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userListFromDb = userService.getAllUsers();
        return ResponseEntity.ok(userListFromDb);
    }





}
