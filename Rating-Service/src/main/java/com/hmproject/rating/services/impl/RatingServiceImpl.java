package com.hmproject.rating.services.impl;

import com.hmproject.rating.entities.Hotel;
import com.hmproject.rating.entities.Rating;
import com.hmproject.rating.repositories.RatingRepository;
import com.hmproject.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId) {

        List<Rating> ratingsByUserId=  this.ratingRepository.findByUserId(userId);

        List<Rating> ratingList = ratingsByUserId.stream().peek(rating -> {

            ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);

            Hotel hotel = hotelResponseEntity.getBody();
            rating.setHotel(hotel);
//            return rating;

        }).toList();


        return ratingsByUserId;
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
