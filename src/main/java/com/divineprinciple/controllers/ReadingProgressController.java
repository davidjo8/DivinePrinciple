package com.divineprinciple.controllers;

import com.divineprinciple.models.ReadingProgress;
import com.divineprinciple.models.User;
import com.divineprinciple.services.ReadingProgressService;
import com.divineprinciple.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ReadingProgressController {
    private final ReadingProgressService readingProgressService;
    private final UserService userService;

    public ReadingProgressController(ReadingProgressService readingProgressService, UserService userService) {
        this.readingProgressService = readingProgressService;
        this.userService = userService;
    }

    // Get progress for a user by email
    @GetMapping("/{email}")
    public ResponseEntity<Object> getProgress(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
    
        Optional<ReadingProgress> progress = readingProgressService.getUserProgress(user.get());
        return progress.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok("No progress found"));
    }
    

    // Update or create reading progress
    @PostMapping("/update")
    public ResponseEntity<?> updateProgress(@RequestParam String email, @RequestParam int chapter, @RequestParam int paragraph) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        ReadingProgress progress = readingProgressService.getUserProgress(user.get())
                .orElse(new ReadingProgress());

        progress.setUser(user.get());
        progress.setChapter(chapter);
        progress.setParagraph(paragraph);
        readingProgressService.saveProgress(progress);

        return ResponseEntity.ok("Progress updated successfully");
    }
}
