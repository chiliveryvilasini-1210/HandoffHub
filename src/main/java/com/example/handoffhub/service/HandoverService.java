package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Handover;
import com.example.handoffhub.entity.Project;
import com.example.handoffhub.entity.User;
import com.example.handoffhub.repository.HandoverRepository;
import com.example.handoffhub.repository.ProjectRepository;
import com.example.handoffhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandoverService {

    private final HandoverRepository handoverRepository;
    private final ActivityService activityService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public HandoverService(
            HandoverRepository handoverRepository,
            ActivityService activityService,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.handoverRepository = handoverRepository;
        this.activityService = activityService;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Create a new handover
    public Handover createHandover(Handover handover) {

        if (handover.getTitle() == null
                || handover.getTitle().isBlank()) {
            throw new RuntimeException("Handover title is required");
        }

        if (handover.getDescription() == null
                || handover.getDescription().isBlank()) {
            throw new RuntimeException("Handover description is required");
        }

        if (handover.getProject() == null
                || handover.getProject().getId() == null) {
            throw new RuntimeException("Project ID is required");
        }

        if (handover.getFromUser() == null
                || handover.getFromUser().getId() == null) {
            throw new RuntimeException("From User ID is required");
        }

        if (handover.getToUser() == null
                || handover.getToUser().getId() == null) {
            throw new RuntimeException("To User ID is required");
        }

        Long projectId = handover.getProject().getId();
        Long fromUserId = handover.getFromUser().getId();
        Long toUserId = handover.getToUser().getId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found: " + projectId));

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() ->
                        new RuntimeException("From user not found: " + fromUserId));

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() ->
                        new RuntimeException("To user not found: " + toUserId));

        handover.setProject(project);
        handover.setFromUser(fromUser);
        handover.setToUser(toUser);

        if (handover.getStatus() == null
                || handover.getStatus().isBlank()) {
            handover.setStatus("DRAFT");
        }

        if (handover.getReadinessScore() == null) {
            handover.setReadinessScore(0);
        }

        return handoverRepository.save(handover);
    }

    // Get all handovers
    public List<Handover> getAllHandovers() {
        return handoverRepository.findAll();
    }

    // Get a handover by ID
    public Handover getHandoverById(Long id) {
        return handoverRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Handover not found"));
    }

    // Submit a draft handover
    public Handover submitHandover(Long id) {

        Handover handover = getHandoverById(id);

        if (!"DRAFT".equalsIgnoreCase(handover.getStatus())) {
            throw new RuntimeException(
                    "Only a draft handover can be submitted");
        }

        handover.setStatus("SUBMITTED");
        Handover savedHandover = handoverRepository.save(handover);

        Activity activity = new Activity();
        activity.setAction("Handover Submitted");
        activity.setHandover(savedHandover);
        activityService.createActivity(activity);

        return savedHandover;
    }

    // Accept an approved handover
    public Handover acceptHandover(Long id) {

        Handover handover = getHandoverById(id);

        if (!"APPROVED".equalsIgnoreCase(handover.getStatus())) {
            throw new RuntimeException(
                    "Handover must be approved before acceptance");
        }

        handover.setStatus("ACCEPTED");
        Handover savedHandover = handoverRepository.save(handover);

        Activity activity = new Activity();
        activity.setAction("Handover Accepted");
        activity.setHandover(savedHandover);
        activityService.createActivity(activity);

        return savedHandover;
    }
}