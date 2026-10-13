
package com.example.handoffhub.service;

import com.example.handoffhub.entity.Issue;
import com.example.handoffhub.entity.Knowledge;
import com.example.handoffhub.entity.Task;
import com.example.handoffhub.repository.IssueRepository;
import com.example.handoffhub.repository.KnowledgeRepository;
import com.example.handoffhub.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeRiskService {

    private final KnowledgeRepository knowledgeRepository;
    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;

    public KnowledgeRiskService(
            KnowledgeRepository knowledgeRepository,
            IssueRepository issueRepository,
            TaskRepository taskRepository) {

        this.knowledgeRepository = knowledgeRepository;
        this.issueRepository = issueRepository;
        this.taskRepository = taskRepository;
    }

    public Map<String, Object> analyzeRisk(Long handoverId) {

        List<Knowledge> knowledge =
                knowledgeRepository.findByHandoverId(handoverId);

        List<Issue> issues =
                issueRepository.findAll().stream()
                        .filter(issue -> issue.getHandover() != null
                                && handoverId.equals(
                                issue.getHandover().getId()))
                        .toList();

        List<Task> tasks =
                taskRepository.findAll().stream()
                        .filter(task -> task.getHandover() != null
                                && handoverId.equals(
                                task.getHandover().getId()))
                        .toList();

        long openIssues = issues.stream()
                .filter(issue -> issue.getStatus() != null
                        && !issue.getStatus().equalsIgnoreCase("RESOLVED")
                        && !issue.getStatus().equalsIgnoreCase("CLOSED"))
                .count();

        long pendingTasks = tasks.stream()
                .filter(task -> task.getStatus() != null
                        && !task.getStatus().equalsIgnoreCase("COMPLETED")
                        && !task.getStatus().equalsIgnoreCase("DONE"))
                .count();

        String riskLevel;

        if (knowledge.isEmpty() || openIssues >= 3 || pendingTasks >= 3) {
            riskLevel = "HIGH";
        } else if (knowledge.size() == 1 || openIssues > 0
                || pendingTasks > 0) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("handoverId", handoverId);
        result.put("riskLevel", riskLevel);
        result.put("knowledgeRecords", knowledge.size());
        result.put("issuesRecorded", issues.size());
        result.put("openIssues", openIssues);
        result.put("pendingTasks", pendingTasks);

        return result;
    }
}
