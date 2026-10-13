
package com.example.handoffhub.controller;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    // Get all activities across every handover
    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    // Get activities for one specific handover
    @GetMapping("/handover/{handoverId}")
    public List<Activity> getHandoverActivities(
            @PathVariable Long handoverId) {
        return activityService.getHandoverActivities(handoverId);
    }
}
