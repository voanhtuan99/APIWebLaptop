package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.UserDTO;
import com.example.apiweblaptop.entity.User;

import java.util.List;

public interface UserService {
    public List<User> retrieveUsers();

    public User getUser(Long userId);

    public UserDTO saveUser(UserDTO user);

    public void deleteUser(Long userId);

    public void updateUser(User user);


}
