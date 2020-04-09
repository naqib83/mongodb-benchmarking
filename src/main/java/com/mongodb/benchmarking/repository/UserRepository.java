package com.mongodb.benchmarking.repository;

import com.mongodb.benchmarking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    @Query("{ 'name' : ?0 }")
    List<User> findUsersByName(String name);
}
