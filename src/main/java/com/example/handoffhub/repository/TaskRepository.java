package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByHandoverId(Long handoverId);
}