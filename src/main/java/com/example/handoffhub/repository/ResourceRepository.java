package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    long countByHandoverId(Long handoverId);
}