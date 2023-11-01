package com.hmproject.user.service.services.impl;

import com.hmproject.user.service.entities.Hotel;
import com.hmproject.user.service.entities.Rating;
import com.hmproject.user.service.entities.User;
import com.hmproject.user.service.exceptions.ResourceNotFoundException;
import com.hmproject.user.service.repositories.UserRepository;
import com.hmproject.user.service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        // generate unique random userID
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //getting user details from db with help of this.userRepository

        User user= this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with given id is not found on server"+ userId));

        // to fetch details about rating we need to get those from RATING-SERVICE
        // so in our RATING-SERVICE there must be some api which should take uesrId and return its corresponding ratings
//      @GetMapping("/users/{userId}")   -> rating-service

        //  we need some client which can contact with http server with the help of http api/client  -> we can use REST-TEMPLATE of feign client

//       ArrayList<Rating> ratingsByUser= restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), ArrayList.class);
        Rating[] ratingsByUser= restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsByUser);

        List<Rating> ratings= Arrays.stream(ratingsByUser).toList();

        List<Rating> ratingList= ratings.stream().map(rating -> {

            // now we also need hotel details on which user had rated
            // api call to hotel service to get the hotel

            ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelResponseEntity.getBody();
            logger.info("response status code -> {}", hotelResponseEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).toList();

        // set hotel to rating then return rating



        user.setRatings(ratings);
        return user;
    }
}
