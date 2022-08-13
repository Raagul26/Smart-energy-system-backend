package com.switchenergysystem.app.repository;

import com.switchenergysystem.app.entity.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepo extends MongoRepository<Provider, String> {

    Provider findByName(String name);

}
