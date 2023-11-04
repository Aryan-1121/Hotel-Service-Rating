package com.hmproject.user.service.controllers;

import com.hmproject.user.service.services.UserService;
import com.hmproject.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hmproject.user.service.entities.User;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final Logger logger= LoggerFactory.getLogger(UserServiceImpl .class);;
    //save

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser= userService.saveUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    // get single user

    // this method call downline implementation contains communication with different microservices, therefore we have to create a circuit breaker here and a fallBack method too.

    @GetMapping(value = "/{userId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackMethod")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User userFromDb= userService.getUser(userId);
        return ResponseEntity.ok(userFromDb);

    }

//     creating fallBack method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallbackMethod(String userId, Exception ex){
        logger.info("this is fallback being executed coz service is down  : - {}",ex.getMessage());
        User user =User.builder()
                .name("Dummy name")
                .userId("999999")
                .about("this is dummy user created because some service is down !!")
                .email("dummy@gmail.com").build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // get all user

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userListFromDb = userService.getAllUsers();
        return ResponseEntity.ok(userListFromDb);
    }


    @PostMapping(value = "/hello",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello there !!");
    }


}
