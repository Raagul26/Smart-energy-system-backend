package com.switchenergysystem.app.controller;

import com.switchenergysystem.app.controller.response.APIResponse;
import com.switchenergysystem.app.entity.User;
import com.switchenergysystem.app.entity.UserLogin;
import com.switchenergysystem.app.service.UserService;
import com.switchenergysystem.app.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody User user) {
        APIResponse response = new APIResponse();
        response.setStatus("Success");
        try {
            response.setData(userService.createUser(user));
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@Valid @RequestBody UserLogin userLogin) {
        APIResponse response = new APIResponse();
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            response.setData(userService.login(userLogin));
            String jwtToken = jwtUtility.generateToken(userLogin.getEmailId(), 10*60*60);
            responseHeader.set("JWTToken", jwtToken);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, responseHeader, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllUsers(@RequestHeader(value = "authorization") String auth) {
        APIResponse response = new APIResponse();
        if (Objects.equals(jwtUtility.validateToken(auth), "admin")) {
            response.setStatus("Success");
            response.setData(userService.getAllUsers());
            response.setMessage("Users data fetched successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Access Denied");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
