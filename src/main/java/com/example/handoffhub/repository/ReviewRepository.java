package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}