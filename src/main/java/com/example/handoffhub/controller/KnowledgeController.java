package com.example.handoffhub.controller;

import com.example.handoffhub.entity.Knowledge;
import com.example.handoffhub.service.KnowledgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping
    public Knowledge createKnowledge(@RequestBody Knowledge knowledge) {
        return knowledgeService.createKnowledge(knowledge);
    }

    @GetMapping
    public List<Knowledge> getAllKnowledge() {
        return knowledgeService.getAllKnowledge();
    }
}
