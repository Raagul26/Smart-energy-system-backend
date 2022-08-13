package com.switchenergysystem.app.controller;

import com.switchenergysystem.app.controller.response.APIResponse;
import com.switchenergysystem.app.entity.Provider;
import com.switchenergysystem.app.entity.User;
import com.switchenergysystem.app.service.ProviderService;
import com.switchenergysystem.app.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Objects;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping
    public ResponseEntity<APIResponse> create(@Valid @RequestBody Provider provider, @RequestHeader(value = "authorization") String auth) {
        APIResponse response = new APIResponse();
        try {
            if (Objects.equals(jwtUtility.validateToken(auth), "admin")) {
                response.setStatus("Success");
                response.setData(providerService.createProvider(provider));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        response.setMessage("Access denied");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProviders() {
        APIResponse response = new APIResponse();
        response.setStatus("Success");
        response.setData(providerService.getAllProviders());
        response.setMessage("Providers data fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-status")
    public ResponseEntity<APIResponse> changeStatus(@PathParam("name") String name, @PathParam("status") String status,
                                                    @RequestHeader(value = "authorization") String auth) {
        APIResponse response = new APIResponse();
        try {
            if (Objects.equals(jwtUtility.validateToken(auth), "admin")) {
                response.setStatus("Success");
                response.setData(providerService.changeStatus(name, status));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        response.setMessage("Access denied");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
