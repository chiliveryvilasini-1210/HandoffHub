package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Issue;
import com.example.handoffhub.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ActivityService activityService;

    public IssueService(IssueRepository issueRepository,
                        ActivityService activityService) {
        this.issueRepository = issueRepository;
        this.activityService = activityService;
    }

    public Issue createIssue(Issue issue) {

        // Save the issue first
        Issue savedIssue = issueRepository.save(issue);

        // Create activity record
        Activity activity = new Activity();
        activity.setAction("Issue Added");
        activity.setHandover(savedIssue.getHandover());

        activityService.createActivity(activity);

        return savedIssue;
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }
}