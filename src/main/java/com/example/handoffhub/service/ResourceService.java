package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Resource;
import com.example.handoffhub.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ActivityService activityService;

    public ResourceService(ResourceRepository resourceRepository,
                           ActivityService activityService) {
        this.resourceRepository = resourceRepository;
        this.activityService = activityService;
    }

    public Resource createResource(Resource resource) {

        // Save the resource first
        Resource savedResource = resourceRepository.save(resource);

        // Create activity record
        Activity activity = new Activity();
        activity.setAction("Resource Added");
        activity.setHandover(savedResource.getHandover());

        activityService.createActivity(activity);

        return savedResource;
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
}