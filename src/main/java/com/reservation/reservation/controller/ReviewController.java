package com.reservation.reservation.controller;

import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Review;
import com.reservation.reservation.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Response> getAllReviews() {
        Response response = reviewService.getAllReviews();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getReviewById(@PathVariable Long id) {
        Response response = reviewService.getReviewById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    public ResponseEntity<Response> createReview(@RequestParam Long labId, @RequestParam Long userId, @RequestBody Review review) {
        Response response = reviewService.createReview(labId, userId, review);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Response response = reviewService.updateReview(id, review);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteReview(@PathVariable Long id) {
        Response response = reviewService.deleteReview(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
