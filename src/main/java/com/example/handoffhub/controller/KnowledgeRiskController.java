
package com.example.handoffhub.controller;

import com.example.handoffhub.service.KnowledgeRiskService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/knowledge-risk")
public class KnowledgeRiskController {

    private final KnowledgeRiskService knowledgeRiskService;

    public KnowledgeRiskController(
            KnowledgeRiskService knowledgeRiskService) {
        this.knowledgeRiskService = knowledgeRiskService;
    }

    @GetMapping("/{handoverId}")
    public Map<String, Object> getKnowledgeRisk(
            @PathVariable Long handoverId) {
        return knowledgeRiskService.analyzeRisk(handoverId);
    }
}
