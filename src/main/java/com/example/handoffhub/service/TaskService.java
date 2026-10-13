package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Handover;
import com.example.handoffhub.entity.Task;
import com.example.handoffhub.repository.HandoverRepository;
import com.example.handoffhub.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ActivityService activityService;
    private final HandoverRepository handoverRepository;

    public TaskService(
            TaskRepository taskRepository,
            ActivityService activityService,
            HandoverRepository handoverRepository) {
        this.taskRepository = taskRepository;
        this.activityService = activityService;
        this.handoverRepository = handoverRepository;
    }

    @Transactional
    public Task createTask(Task task) {

        if (task.getHandover() == null
                || task.getHandover().getId() == null) {
            throw new IllegalArgumentException("Handover ID is required.");
        }

        Long handoverId = task.getHandover().getId();

        Handover handover = handoverRepository.findById(handoverId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Handover not found."));

        String status = handover.getStatus();

        if (!"DRAFT".equalsIgnoreCase(status)
                && !"SUBMITTED".equalsIgnoreCase(status)
                && !"ACCEPTED".equalsIgnoreCase(status)) {
            throw new IllegalStateException(
                    "Tasks can only be added to draft, submitted, or accepted handovers.");
        }

        task.setHandover(handover);

        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("PENDING");
        }

        Task savedTask = taskRepository.save(task);

        Activity activity = new Activity();
        activity.setAction("Task Added");
        activity.setHandover(handover);
        activityService.createActivity(activity);

        return savedTask;
    }

    @Transactional
    public Task completeTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found."));

        task.setStatus("COMPLETED");

        Task savedTask = taskRepository.save(task);

        Activity activity = new Activity();
        activity.setAction("Task Completed");
        activity.setHandover(savedTask.getHandover());
        activityService.createActivity(activity);

        return savedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
