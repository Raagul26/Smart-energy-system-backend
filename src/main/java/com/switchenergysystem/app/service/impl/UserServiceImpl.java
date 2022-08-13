package com.switchenergysystem.app.service.impl;

import com.switchenergysystem.app.entity.User;
import com.switchenergysystem.app.entity.UserLogin;
import com.switchenergysystem.app.repository.UserRepo;
import com.switchenergysystem.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User user) {

        if (userRepo.findByEmailId(user.getEmailId()) != null) {
            throw new RuntimeException("Email Id already exists");
        }

        user.setStatus("Active");
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User login(UserLogin userLogin) {
        User user = userRepo.findByEmailId(userLogin.getEmailId());

        if (user == null) {
            throw new RuntimeException("Invalid EmailId");
        } else if (!userLogin.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Incorrect Password");
        }

        return user;

    }

}
