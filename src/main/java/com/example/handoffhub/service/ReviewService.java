package com.example.handoffhub.service;

import com.example.handoffhub.entity.Activity;
import com.example.handoffhub.entity.Handover;
import com.example.handoffhub.entity.Review;
import com.example.handoffhub.repository.HandoverRepository;
import com.example.handoffhub.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HandoverRepository handoverRepository;
    private final ActivityService activityService;

    public ReviewService(
            ReviewRepository reviewRepository,
            HandoverRepository handoverRepository,
            ActivityService activityService) {

        this.reviewRepository = reviewRepository;
        this.handoverRepository = handoverRepository;
        this.activityService = activityService;
    }

    public Review createReview(Review review) {

        review.setReviewedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        Handover handover = review.getHandover();

        if (review.getDecision().equalsIgnoreCase("APPROVED")) {
            handover.setStatus("APPROVED");
        }
        else if (review.getDecision().equalsIgnoreCase("REJECTED")) {
            handover.setStatus("DRAFT");
        }

        handoverRepository.save(handover);

        Activity activity = new Activity();

        activity.setAction(
                review.getDecision().equalsIgnoreCase("APPROVED")
                        ? "Handover Approved"
                        : "Handover Rejected"
        );

        activity.setHandover(handover);
        activity.setUser(review.getReviewer());

        activityService.createActivity(activity);

        return savedReview;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}