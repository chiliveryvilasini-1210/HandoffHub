
package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity createActivity(Activity activity) {
        activity.setCreatedAt(LocalDateTime.now());
        return activityRepository.save(activity);
    }

    public List<Activity> getHandoverActivities(Long handoverId) {
        return activityRepository
                .findByHandoverIdOrderByCreatedAtDesc(handoverId);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAllByOrderByCreatedAtDesc();
    }
}
