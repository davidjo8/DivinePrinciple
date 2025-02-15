package com.divineprinciple.controllers;

import com.divineprinciple.models.ReadingProgress;
import com.divineprinciple.models.User;
import com.divineprinciple.services.UserService;
import com.divineprinciple.services.ReadingProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ReadingProgressService readingProgressService;

    @Autowired
    public UserController(UserService userService, ReadingProgressService readingProgressService) {
        this.userService = userService;
        this.readingProgressService = readingProgressService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }

        existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // Login a user (simple example, no password hashing)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // In a real application, you'd compare passwords securely
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
    }

    // Get reading progress for a user
@GetMapping("/{userId}/progress")
public ResponseEntity<?> getReadingProgress(@PathVariable Long userId) {
    Optional<User> user = userService.findUserById(userId);  // Modify this method in UserService to fetch by ID
    if (user.isPresent()) {
        Optional<ReadingProgress> progress = readingProgressService.getUserProgress(user.get());
        if (progress.isPresent()) {
            return new ResponseEntity<>(progress.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Progress not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
}

}