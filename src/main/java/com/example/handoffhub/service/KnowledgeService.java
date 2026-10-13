package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Knowledge;
import com.example.handoffhub.repository.KnowledgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;
    private final ActivityService activityService;

    public KnowledgeService(KnowledgeRepository knowledgeRepository,
                            ActivityService activityService) {
        this.knowledgeRepository = knowledgeRepository;
        this.activityService = activityService;
    }

    public Knowledge createKnowledge(Knowledge knowledge) {

        // Save the knowledge first
        Knowledge savedKnowledge = knowledgeRepository.save(knowledge);

        // Create activity record
        Activity activity = new Activity();
        activity.setAction("Knowledge Added");
        activity.setHandover(savedKnowledge.getHandover());

        activityService.createActivity(activity);

        return savedKnowledge;
    }

    public List<Knowledge> getAllKnowledge() {
        return knowledgeRepository.findAll();
    }
}