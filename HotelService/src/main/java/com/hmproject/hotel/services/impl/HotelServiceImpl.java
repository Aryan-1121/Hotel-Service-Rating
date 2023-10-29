package com.hmproject.hotel.services.impl;

import com.hmproject.hotel.entities.Hotel;
import com.hmproject.hotel.repositories.HotelRepository;
import com.hmproject.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmproject.hotel.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel create(Hotel hotel) {

        String uniqueRandomID = UUID.randomUUID().toString();
        hotel.setId(uniqueRandomID);
        return this.hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("nothing find with given id"+ id));
    }
}
