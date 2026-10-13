package com.example.handoffhub.service;

import com.example.handoffhub.entity.Handover;
import com.example.handoffhub.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ReadinessScoreService {

    private final TaskRepository taskRepository;
    private final IssueRepository issueRepository;
    private final KnowledgeRepository knowledgeRepository;
    private final ResourceRepository resourceRepository;
    private final HandoverRepository handoverRepository;

    public ReadinessScoreService(
            TaskRepository taskRepository,
            IssueRepository issueRepository,
            KnowledgeRepository knowledgeRepository,
            ResourceRepository resourceRepository,
            HandoverRepository handoverRepository) {

        this.taskRepository = taskRepository;
        this.issueRepository = issueRepository;
        this.knowledgeRepository = knowledgeRepository;
        this.resourceRepository = resourceRepository;
        this.handoverRepository = handoverRepository;
    }

    public Integer calculateScore(Handover handover) {

        int score = 0;

        // Project information
        if (handover.getProject() != null) {
            score += 20;
        }

        // Tasks
        if (taskRepository.countByHandoverId(handover.getId()) > 0) {
            score += 20;
        }

        // Issues
        if (issueRepository.countByHandoverId(handover.getId()) > 0) {
            score += 20;
        }

        // Knowledge
        if (knowledgeRepository.countByHandoverId(handover.getId()) > 0) {
            score += 20;
        }

        // Resources
        if (resourceRepository.countByHandoverId(handover.getId()) > 0) {
            score += 20;
        }

        // Save the calculated score
        handover.setReadinessScore(score);
        handoverRepository.save(handover);

        return score;
    }
}
