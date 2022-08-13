package com.switchenergysystem.app.service;

import com.switchenergysystem.app.entity.SmartMeter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SmartMeterService {

    SmartMeter createSmartMeter(SmartMeter smartMeter);

    List<SmartMeter> getAllSmartMeters();

    List<SmartMeter> getUserSmartMeters(String userId, String status);

    List<SmartMeter> getSmartMetersByStatus(String status);

    SmartMeter changeProvider(String meterId, String providerName);

    SmartMeter insertReading(String meterId, double reading);

    SmartMeter changeStatus(String id, String status);

}
