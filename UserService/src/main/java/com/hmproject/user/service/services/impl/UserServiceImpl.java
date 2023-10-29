package com.hmproject.user.service.services.impl;

import com.hmproject.user.service.entities.User;
import com.hmproject.user.service.exceptions.ResourceNotFoundException;
import com.hmproject.user.service.repositories.UserRepository;
import com.hmproject.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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
        return this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with given id is not found on server"+ userId));
    }
}