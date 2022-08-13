package com.switchenergysystem.app.service.impl;

import com.switchenergysystem.app.entity.Reading;
import com.switchenergysystem.app.entity.SmartMeter;
import com.switchenergysystem.app.repository.SmartMeterRepo;
import com.switchenergysystem.app.service.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmartMeterServiceImpl implements SmartMeterService {

    @Autowired
    private SmartMeterRepo smartMeterRepo;

    @Override
    public SmartMeter createSmartMeter(SmartMeter smartMeter) {

        SmartMeter smartMeter1 = smartMeterRepo.findByMeterIdAndStatus(smartMeter.getMeterId(), "enabled");

        if (smartMeter1 != null) {
            throw new RuntimeException("Smart meter already exists");
        }

        smartMeter.setStatus("enabled");
        return smartMeterRepo.save(smartMeter);

    }

    @Override
    public List<SmartMeter> getAllSmartMeters() {
        return smartMeterRepo.findAll();
    }

    @Override
    public List<SmartMeter> getUserSmartMeters(String userId, String status) {
        return smartMeterRepo.findByEmailIdAndStatus(userId, status);
    }

    @Override
    public List<SmartMeter> getSmartMetersByStatus(String status) {
        return smartMeterRepo.findByStatus(status);
    }

    @Override
    public SmartMeter changeProvider(String meterId, String providerName) {
        return null;
    }

    @Override
    public SmartMeter insertReading(String meterId, double reading) {
        SmartMeter smartMeter = smartMeterRepo.findByMeterIdAndStatus(meterId, "enabled");

        if (smartMeter == null) {
            throw  new RuntimeException("Smart meter not found");
        }

        smartMeter.getReadings().add(new Reading(new Date(), reading));
        return smartMeterRepo.save(smartMeter);
    }

    @Override
    public SmartMeter changeStatus(String id, String status) {
        SmartMeter smartMeter = smartMeterRepo.findByMeterIdAndStatus(id, "enabled");

        if (smartMeter == null) {
            throw  new RuntimeException("Smart meter not found");
        }

        smartMeter.setStatus(status);
        return smartMeterRepo.save(smartMeter);

    }

}
