
package com.example.handoffhub.repository;

import com.example.handoffhub.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Get activities for one handover, newest first
    List<Activity> findByHandoverIdOrderByCreatedAtDesc(Long handoverId);

    // Get activities across all handovers, newest first
    List<Activity> findAllByOrderByCreatedAtDesc();
}
