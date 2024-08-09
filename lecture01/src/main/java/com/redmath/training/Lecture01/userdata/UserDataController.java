package com.redmath.training.Lecture01.userdata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class UserDataController {
    @GetMapping("/api/v1/user-data")
    public ResponseEntity<Map<String, Object>> userData(){
        return ResponseEntity.ok(Map.of("id", 19, "name", "Abdul Muiz","createdAt", LocalDateTime.now()));
    }
}
