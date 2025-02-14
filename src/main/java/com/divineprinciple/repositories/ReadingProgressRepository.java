package com.divineprinciple.repositories;

import com.divineprinciple.models.ReadingProgress;
import com.divineprinciple.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {
    Optional<ReadingProgress> findByUser(User user);
}