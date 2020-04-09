package com.mongodb.benchmarking.controller;

import com.mongodb.benchmarking.entity.User;
import com.mongodb.benchmarking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity postUsers() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for(int i = 0; i<200; i++) {
            Thread.sleep(2000);
            ResponseEntity<User[]> response = restTemplate.getForEntity("https://my.api.mockaroo.com/user-address-cars.json?key=2477e480", User[].class);
            User[] users = response.getBody();
            try{
                userService.saveAllUser(Arrays.asList(users));
            } catch (Exception e){
                e.getMessage();
            }

        }
        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(value = "/byName/{name}")
    public List<User> getUsersByName(@PathVariable("name") String name){
        return userService.findUserByName(name);
    }

    @GetMapping(value = "/byState/{state}")
    public List<User> getUsersByState(@PathVariable("state") String state){
        return userService.findByState(state);
    }

    @GetMapping(value = "/byCarMake/{make}")
    public List<User> getUsersByCarMake(@PathVariable("make") String make){
        return userService.findByCarMake(make);
    }

    @GetMapping(value = "/byCarMakeAndState/{make}/{state}")
    public List<User> getUsersByCarMakeAndState(@PathVariable("make") String make, @PathVariable("state") String state){
        return userService.findByCarMakeAndState(make, state);
    }
}
