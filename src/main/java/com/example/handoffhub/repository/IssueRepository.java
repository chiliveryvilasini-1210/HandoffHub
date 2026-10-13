package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    long countByHandoverId(Long handoverId);
}
