package com.mongodb.benchmarking.service;

import com.mongodb.benchmarking.entity.User;
import com.mongodb.benchmarking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(User user){
        mongoTemplate.insert(user);
    }

    public void saveAllUser(List<User> users){
        mongoTemplate.insertAll(users);
    }

    public List<User> findUserByName(String name) {
        return userRepository.findUsersByName(name);
    };

    public List<User> findByState(String state){
        Query query = new Query(Criteria.where("address.state").is(state));
        query.limit(10);
        return mongoTemplate.find(query, User.class);
    }

    public List<User> findByCarMake(String make){
        Query query = new Query(Criteria.where("cars.make").is(make));
        query.limit(10);
        return mongoTemplate.find(query, User.class);
    }

    public List<User> findByCarMakeAndState(String make, String state){
        Query query = new Query(Criteria.where("cars.make").is(make));
        query.addCriteria(Criteria.where("address.state").is(state));
        return mongoTemplate.find(query, User.class);
    }
}
