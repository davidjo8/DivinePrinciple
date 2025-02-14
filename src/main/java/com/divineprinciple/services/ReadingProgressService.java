package com.divineprinciple.services;

import com.divineprinciple.models.ReadingProgress;
import com.divineprinciple.models.User;
import com.divineprinciple.repositories.ReadingProgressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadingProgressService {
    private final ReadingProgressRepository readingProgressRepository;

    public ReadingProgressService(ReadingProgressRepository repository) {
        this.readingProgressRepository = repository;
    }

    public Optional<ReadingProgress> getUserProgress(User user) {
        return readingProgressRepository.findByUser(user);
    }

    public ReadingProgress saveProgress(ReadingProgress progress) {
        return readingProgressRepository.save(progress);
    }
}