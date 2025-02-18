package com.divineprinciple.controllers;

import com.divineprinciple.models.User;
import com.divineprinciple.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String token = authService.registerUser(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Optional<String> token = authService.authenticate(email, password);
        return token.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).body("Invalid credentials"));
    }
}
