package com.mongodb.benchmarking.service;

import com.mongodb.benchmarking.entity.User;
import com.mongodb.benchmarking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class UserService{
    //Learn to process data in parallel
    //Now processor takes most of the speed just to insert the data.
    Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(User user){
        Instant start = Instant.now();
        mongoTemplate.insert(user);
        getDurationOfProcess(start, Instant.now(), 1);
    }

    public void saveAllUser(List<User> users){
        Instant start = Instant.now();
        mongoTemplate.insertAll(users);
        getDurationOfProcess(start, Instant.now(), users.size());
    }

    public List<User> findUserByName(String name) {
        Instant start = Instant.now();
        List<User> users = userRepository.findUsersByName(name);
        getDurationOfProcess(start, Instant.now(), users.size());

        return users;
    };

    public List<User> findByState(String state){
        Query query = new Query(Criteria.where("address.state").is(state));
        query.limit(10);

        Instant start = Instant.now();
        List<User> users = mongoTemplate.find(query, User.class);
        getDurationOfProcess(start, Instant.now(), users.size());

        return users;
    }

    public List<User> findByCarMake(String make){
        Query query = new Query(Criteria.where("cars.make").is(make));
        query.limit(10);

        Instant start = Instant.now();
        List<User> users = mongoTemplate.find(query, User.class);
        getDurationOfProcess(start, Instant.now(), users.size());

        return users;
    }

    public List<User> findByCarMakeAndState(String make, String state){
        Query query = new Query(Criteria.where("cars.make").is(make));
        query.addCriteria(Criteria.where("address.state").is(state));

        Instant start = Instant.now();
        List<User> users = mongoTemplate.find(query, User.class);
        getDurationOfProcess(start, Instant.now(), users.size());

        return users;
    }

    private long getDurationOfProcess(Instant start, Instant end, Integer size){
        long duration = Duration.between(start, end).toMillis();
        logger.info("saving list of users of size {}", size);
        logger.info("Total time {}", duration);
        return duration;
    }
}
