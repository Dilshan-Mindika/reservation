package com.reservation.reservation.service.impl;

import com.reservation.reservation.dto.ReviewDTO;
import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Review;
import com.reservation.reservation.repo.ReviewRepository;
import com.reservation.reservation.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Response getAllReviews() {
        Response response = new Response();
        try {
            List<Review> reviews = reviewRepository.findAll();
            List<ReviewDTO> reviewDTOs = Utils.mapReviewListEntityToReviewListDTO(reviews);
            response.setStatusCode(200);
            response.setMessage("Reviews retrieved successfully");
            response.setReviewList(reviewDTOs);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all reviews: " + e.getMessage());
        }
        return response;
    }

    public Response getReviewById(Long id) {
        Response response = new Response();
        try {
            Review review = reviewRepository.findById(id).orElse(null);
            if (review != null) {
                ReviewDTO reviewDTO = Utils.mapReviewEntityToReviewDTO(review);
                response.setStatusCode(200);
                response.setMessage("Review retrieved successfully");
                response.setReview(reviewDTO);
            } else {
                response.setStatusCode(404);
                response.setMessage("Review not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting review by ID: " + e.getMessage());
        }
        return response;
    }

    public Response createReview(ReviewDTO reviewDTO) {
        Response response = new Response();
        try {
            Review review = Utils.mapReviewDTOToReviewEntity(reviewDTO);
            Review savedReview = reviewRepository.save(review);
            ReviewDTO createdReviewDTO = Utils.mapReviewEntityToReviewDTO(savedReview);
            response.setStatusCode(200);
            response.setMessage("Review created successfully");
            response.setReview(createdReviewDTO);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating review: " + e.getMessage());
        }
        return response;
    }

    public Response updateReview(Long id, ReviewDTO reviewDTO) {
        Response response = new Response();
        try {
            Review review = reviewRepository.findById(id).orElse(null);
            if (review != null) {
                review.setComment(reviewDTO.getComment());
                review.setRating(reviewDTO.getRating());
                review.setUser(Utils.mapUserDTOToUserEntity(reviewDTO.getUser()));
                review.setLab(Utils.mapLabDTOToLabEntity(reviewDTO.getLab()));

                Review updatedReview = reviewRepository.save(review);
                ReviewDTO updatedReviewDTO = Utils.mapReviewEntityToReviewDTO(updatedReview);
                response.setStatusCode(200);
                response.setMessage("Review updated successfully");
                response.setReview(updatedReviewDTO);
            } else {
                response.setStatusCode(404);
                response.setMessage("Review not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating review: " + e.getMessage());
        }
        return response;
    }

    public Response deleteReview(Long id) {
        Response response = new Response();
        try {
            reviewRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("Review deleted successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting review: " + e.getMessage());
        }
        return response;
    }
}
