package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Handover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandoverRepository extends JpaRepository<Handover, Long> {

    long countByStatus(String status);

}
