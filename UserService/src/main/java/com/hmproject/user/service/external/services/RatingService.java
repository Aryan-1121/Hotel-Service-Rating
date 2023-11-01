package com.hmproject.user.service.external.services;

import com.hmproject.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    //get



    // POST
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(Rating rating);      // no need for @RequestBody it will automatically get used

    //PUT  / UPDATE

    // we havent made any api for update in RATING-SERVICE yet ,,,,, so this won't work as of now
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, Rating rating);


    @GetMapping("/rating/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);

}
