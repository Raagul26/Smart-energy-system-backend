package com.switchenergysystem.app.service.impl;

import com.switchenergysystem.app.entity.Provider;
import com.switchenergysystem.app.entity.Reading;
import com.switchenergysystem.app.entity.SmartMeter;
import com.switchenergysystem.app.repository.ProviderRepo;
import com.switchenergysystem.app.repository.SmartMeterRepo;
import com.switchenergysystem.app.service.SmartMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SmartMeterServiceImpl implements SmartMeterService {

    @Autowired
    private SmartMeterRepo smartMeterRepo;

    @Autowired
    private ProviderRepo providerRepo;

    @Override
    public SmartMeter createSmartMeter(SmartMeter smartMeter) {

        SmartMeter smartMeter1 = smartMeterRepo.findByMeterIdAndStatus(smartMeter.getMeterId(), "enabled");

        if (smartMeter1 != null) {
            throw new RuntimeException("Smart meter already exists");
        }

        smartMeter.setStatus("pending_approval");
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
            throw new RuntimeException("Smart meter not found");
        }

        if (Objects.isNull(smartMeter.getReadings())) {
            smartMeter.setReadings(new ArrayList<>());
        }

        smartMeter.getReadings().add(new Reading(new Date(), reading));
        return smartMeterRepo.save(smartMeter);
    }

    @Override
    public SmartMeter changeStatus(String id, String status) {

        SmartMeter smartMeter = null;
        smartMeter = smartMeterRepo.findByMeterIdAndStatus(id, "enabled");

        if (smartMeter == null) {
            smartMeter = smartMeterRepo.findByMeterIdAndStatus(id, "pending_approval");
            if (smartMeter == null) {
                throw new RuntimeException("Smart meter not found");
            }
        }

        smartMeter.setStatus(status);
        return smartMeterRepo.save(smartMeter);

    }

    public Double calculate(String meterId) throws Exception {

        try {
            SmartMeter smartMeter = smartMeterRepo.findByMeterIdAndStatus(meterId, "enabled");
            List<Reading> readings = smartMeter.getReadings();
            int previousTime = 0;
            double totalReading = 0.0;

            for(int i = 1; i<readings.size(); i++) {
                if(readings.get(i).getDate() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(readings.get(previousTime).getDate());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(readings.get(i).getDate());
                    long difference = calendar1.getTimeInMillis() - calendar.getTimeInMillis();
                    double seconds = difference/1000.0;
                    double hours = seconds/(60.0 * 60.0);
                    double kw = readings.get(i).getReading() + 0.0;
                    Provider provider = providerRepo.findByName(smartMeter.getProviderId());
                    totalReading += (kw * hours) * provider.getAmountPerKwh();
                    previousTime += 1;
                } else {
                    throw new Exception("Value not found");
                }
            }

            return totalReading;
        } catch (HttpClientErrorException.NotFound e) {
            throw new Exception("Smart meter not found");
        }

    }

}
