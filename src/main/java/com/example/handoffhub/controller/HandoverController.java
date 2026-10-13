
package com.example.handoffhub.controller;

import com.example.handoffhub.entity.Handover;
import com.example.handoffhub.service.HandoverService;
import com.example.handoffhub.service.ReadinessScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/handovers")
public class HandoverController {

    private final HandoverService handoverService;
    private final ReadinessScoreService readinessScoreService;

    public HandoverController(
            HandoverService handoverService,
            ReadinessScoreService readinessScoreService) {

        this.handoverService = handoverService;
        this.readinessScoreService = readinessScoreService;
    }

    // Create a new handover
    @PostMapping
    public Handover createHandover(@RequestBody Handover handover) {
        return handoverService.createHandover(handover);
    }

    // Get all handovers
    @GetMapping
    public List<Handover> getAllHandovers() {
        return handoverService.getAllHandovers();
    }

    // Get one handover by ID
    @GetMapping("/{id}")
    public Handover getHandoverById(@PathVariable Long id) {
        return handoverService.getHandoverById(id);
    }

    // Calculate readiness score
    @GetMapping("/{id}/readiness-score")
    public Integer calculateReadinessScore(@PathVariable Long id) {
        Handover handover = handoverService.getHandoverById(id);
        return readinessScoreService.calculateScore(handover);
    }

    // Submit a draft handover
    @PutMapping("/{id}/submit")
    public Handover submitHandover(@PathVariable Long id) {
        return handoverService.submitHandover(id);
    }

    // Accept an approved handover
    @PutMapping("/{id}/accept")
    public Handover acceptHandover(@PathVariable Long id) {
        return handoverService.acceptHandover(id);
    }
}
