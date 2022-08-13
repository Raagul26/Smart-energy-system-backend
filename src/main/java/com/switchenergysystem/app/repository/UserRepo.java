package com.switchenergysystem.app.repository;

import com.switchenergysystem.app.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    @Override
    @Query(value = "{}", fields = "{password:0}")
    List<User> findAll();

    User findByEmailId(String emailId);

}
