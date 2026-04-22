package com.zosh.service;

import com.zosh.model.User;

import java.util.List;

public interface UserService {
    public User getUserProfile(String jwt);

    public List<User> getAllUsers();
}
