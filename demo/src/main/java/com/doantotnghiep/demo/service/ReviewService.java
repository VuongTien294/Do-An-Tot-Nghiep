package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.user.AddReviewRequest;
import com.doantotnghiep.demo.dto.response.admin.ReviewListResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void addReview(AddReviewRequest addReviewRequest);

    //Xem list review cho user
    ReviewListResponse getListReview(Long productId, Pageable pageable);


    void deleteReview(Long id);
}
