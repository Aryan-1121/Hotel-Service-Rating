package com.hmproject.user.service.services;

import com.hmproject.user.service.entities.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    List<User> getAllUsers();           // by default all methods are public in interface

    // get single user from Id
    User getUser(String userId);

    //TODO: DELTE
    //TODO: UPDATE

}
