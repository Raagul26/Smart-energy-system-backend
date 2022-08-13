package com.switchenergysystem.app.service;

import com.switchenergysystem.app.entity.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProviderService {

    Provider createProvider(Provider provider);

    List<Provider> getAllProviders();

    Provider changeStatus(String name, String status);

}
