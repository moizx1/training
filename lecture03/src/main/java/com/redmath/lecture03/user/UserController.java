package com.redmath.lecture03.user;

import com.redmath.lecture03.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId") Long userId){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> get(@RequestParam(name="page", defaultValue = "0") Integer page, @RequestParam(name="page", defaultValue = "1000") Integer size ){
        return ResponseEntity.ok(userService.findAll(page, size));
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @PostMapping("/api/v1/users")
    public ResponseEntity<User> create(@RequestBody User user ){
        user = userService.create(user);
        return ResponseEntity.created(URI.create("/api/v1/users/" + user.getUserId())).body(user);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<User> update(@PathVariable("userId") Long userId, @RequestBody User user) {
        Optional<User> saved = userService.update(userId, user);
        if (saved.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saved.get());
    }
}
