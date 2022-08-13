package com.switchenergysystem.app.service.impl;

import com.switchenergysystem.app.entity.Provider;
import com.switchenergysystem.app.repository.ProviderRepo;
import com.switchenergysystem.app.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepo providerRepo;

    @Override
    public Provider createProvider(Provider provider) {

        Provider providerRepoByName = providerRepo.findByName(provider.getName());

        if (providerRepoByName != null) {
            throw new RuntimeException("Provider already exists");
        }

        provider.setStatus("enabled");
        return providerRepo.save(provider);
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerRepo.findAll();
    }

    @Override
    public Provider changeStatus(String name, String status) {
        Provider provider = providerRepo.findByName(name);

        if (provider == null) {
            throw new RuntimeException("Provider not found");
        }

        provider.setStatus(status);

        return providerRepo.save(provider);
    }


}
