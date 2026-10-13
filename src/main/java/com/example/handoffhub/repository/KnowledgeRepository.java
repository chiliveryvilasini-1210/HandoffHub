package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    List<Knowledge> findByHandoverId(Long handoverId);

    long countByHandoverId(Long handoverId);
}