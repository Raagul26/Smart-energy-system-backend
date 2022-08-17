package com.switchenergysystem.app.controller;

import com.switchenergysystem.app.controller.response.APIResponse;
import com.switchenergysystem.app.entity.SmartMeter;
import com.switchenergysystem.app.service.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/smart-meter")
public class SmartMeterController {

    @Autowired
    private SmartMeterService smartMeterService;

    @PostMapping("/enroll")
    public ResponseEntity<APIResponse> create(@Valid @RequestBody SmartMeter smartMeter) {
        APIResponse response = new APIResponse();

        try {
            response.setData(smartMeterService.createSmartMeter(smartMeter));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllSmartMeters() {
        APIResponse response = new APIResponse();
        response.setMessage("Fetched Successfully");
        response.setData(smartMeterService.getAllSmartMeters());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<APIResponse> getActiveSmartMeters() {
        APIResponse response = new APIResponse();
        response.setMessage("Fetched Successfully");
        response.setData(smartMeterService.getSmartMetersByStatus("enabled"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<APIResponse> getNewSmartMeters() {
        APIResponse response = new APIResponse();
        response.setMessage("Fetched Successfully");
        response.setData(smartMeterService.getSmartMetersByStatus("pending_approval"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getUserSmartMeters(@PathParam("userId") String userId, @PathParam("status") String status) {
        APIResponse response = new APIResponse();
        response.setMessage("Fetched Successfully");
        response.setData(smartMeterService.getUserSmartMeters(userId, status));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/change-status")
    public ResponseEntity<APIResponse> changeStatus(@PathParam("id") String id, @PathParam("status") String status) {
        APIResponse response = new APIResponse();
        try {
            response.setData(smartMeterService.changeStatus(id, status));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reading")
    public ResponseEntity<APIResponse> insertReading(@PathParam("meterId") String meterId, @PathParam("reading") double reading) {
        APIResponse response = new APIResponse();
        System.out.println(meterId + " " + reading);
        try {
            response.setData(smartMeterService.insertReading(meterId, reading));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reading")
    public ResponseEntity<APIResponse> getReading(@PathParam("meterId") String meterId) {
        APIResponse response = new APIResponse();
        try {
            response.setData(smartMeterService.calculate(meterId));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
