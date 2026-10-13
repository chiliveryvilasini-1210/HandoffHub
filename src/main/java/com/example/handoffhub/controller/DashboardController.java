package com.example.handoffhub.controller;

import com.example.handoffhub.repository.HandoverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final HandoverRepository handoverRepository;

    public DashboardController(HandoverRepository handoverRepository) {
        this.handoverRepository = handoverRepository;
    }

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("totalHandovers", handoverRepository.count());
        stats.put("draftHandovers",
                handoverRepository.countByStatus("DRAFT"));
        stats.put("approvedHandovers",
                handoverRepository.countByStatus("APPROVED"));
        stats.put("acceptedHandovers",
                handoverRepository.countByStatus("ACCEPTED"));

        return stats;
    }
}
