package com.switchenergysystem.app.service;

import com.switchenergysystem.app.entity.User;
import com.switchenergysystem.app.entity.UserLogin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User login(UserLogin userLogin);

}
