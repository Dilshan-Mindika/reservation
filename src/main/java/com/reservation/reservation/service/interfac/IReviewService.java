package com.reservation.reservation.service.interfac;

import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Review;

public interface IReviewService {

    Response getAllReviews();
    Response getReviewById(Long id);
    Response createReview(Long labId, Long userId, Review review);
    Response updateReview(Long id, Review review);
    Response deleteReview(Long id);
}
