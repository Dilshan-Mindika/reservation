package com.reservation.reservation.service.impl;

import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Lab;
import com.reservation.reservation.entity.Review;
import com.reservation.reservation.entity.User;
import com.reservation.reservation.exception.OurException;
import com.reservation.reservation.repo.LabRepository;
import com.reservation.reservation.repo.ReviewRepository;
import com.reservation.reservation.repo.UserRepository;
import com.reservation.reservation.service.interfac.IReservingService;
import com.reservation.reservation.service.interfac.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IReservingService reviewService;

    @Autowired
    private LabRepository labRepository;

    @Override
    public Response saveReviews(Long labId, Long userId, Review review) {

        Response response = new Response();

        try {

            Lab lab = labRepository.findById(labId).orElseThrow(() -> new OurException("Lab Not Found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            review.setLab(lab);
            review.setUser(user);
            reviewRepository.save(review);

            response.setStatusCode(200);
            response.setMessage("Review saved successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving the review: " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response getAllReviews() {
        Response response = new Response();
        try {
            List<Review> reviews = reviewRepository.findAll();
            response.setStatusCode(200);
            response.setMessage("Reviews retrieved successfully");
            response.setReviewList(reviews);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all reviews: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getReviewById(Long id) {
        Response response = new Response();
        try {
            Review review = reviewRepository.findById(id).orElse(null);
            if (review != null) {
                response.setStatusCode(200);
                response.setMessage("Review retrieved successfully");
                response.setReview(review);
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

    @Transactional
    @Override
    public Response createReview(Long labId, Long userId, Review review) {
        Response response = new Response();
        try {
            User user = userRepository.findById(userId).orElse(null);
            Lab lab = labRepository.findById(labId).orElse(null);
            if (user != null && lab != null) {
                review.setUser(user);
                review.setLab(lab);
                Review savedReview = reviewRepository.save(review);
                response.setStatusCode(200);
                response.setMessage("Review created successfully");
                response.setReview(savedReview);
            } else {
                response.setStatusCode(404);
                response.setMessage("User or Lab not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating review: " + e.getMessage());
        }
        return response;
    }

    @Transactional
    @Override
    public Response updateReview(Long id, Review review) {
        Response response = new Response();
        try {
            Review existingReview = reviewRepository.findById(id).orElse(null);
            if (existingReview != null) {
                existingReview.setComment(review.getComment());
                existingReview.setRating(review.getRating());
                // Avoid resetting User and Lab if they are not meant to be changed
                Review updatedReview = reviewRepository.save(existingReview);
                response.setStatusCode(200);
                response.setMessage("Review updated successfully");
                response.setReview(updatedReview);
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

    @Transactional
    @Override
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
