package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddReviewRequest;
import com.doantotnghiep.demo.dto.response.admin.ReviewListResponse;
import com.doantotnghiep.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //them user
    @PostMapping("/member/review/add")
    public void addUser(
            @RequestBody(required = true) AddReviewRequest addReviewRequest
    ) {
        reviewService.addReview(addReviewRequest);
    }

    //xem list user
    @GetMapping("/review/list")
    public ReviewListResponse getReviewList(
            @RequestParam(required = false) Long productId,
            Pageable pageable
    ){
        return reviewService.getListReview(productId,pageable);
    }
}
