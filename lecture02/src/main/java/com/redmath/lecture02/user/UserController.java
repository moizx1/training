package com.redmath.lecture02.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId") Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> get(@RequestParam(name="page", defaultValue = "0") Integer page, @RequestParam(name="page", defaultValue = "1000") Integer size ){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
