package com.sobolv.lab4distributed.controller;

import com.sobolv.lab4distributed.entity.User;
import com.sobolv.lab4distributed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        if (!username.equals("") && !password.equals("")) {
            User user = new User(username, password);
            repository.save(user);
            return new ResponseEntity<>(user.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<String> getAllUsers() {
        return new ResponseEntity<>(repository.findAll().toString(), HttpStatus.OK);
    }
}
