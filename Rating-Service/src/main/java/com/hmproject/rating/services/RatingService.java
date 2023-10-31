package com.hmproject.rating.services;

import com.hmproject.rating.entities.Rating;
import com.hmproject.rating.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {

    // create
    Rating create (Rating rating);

    //get all ratings
    List<Rating> getAllRatings();


    // get all ratings by userID
    List<Rating> getRatingsByUserId(String userId);

    // get all by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);

}
