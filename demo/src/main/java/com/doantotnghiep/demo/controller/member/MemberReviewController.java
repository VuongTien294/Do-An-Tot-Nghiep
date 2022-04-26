package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddReviewRequest;
import com.doantotnghiep.demo.dto.response.admin.ReviewListResponse;
import com.doantotnghiep.demo.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberReviewController {
    private final ReviewService reviewService;

    @ApiOperation("Api cho khách hàng comment và đánh giá sô sao")
    @PostMapping("/member/review/add")
    public void addUser(
            @RequestBody(required = true) AddReviewRequest addReviewRequest
    ) {
        reviewService.addReview(addReviewRequest);
    }

    @ApiOperation("Api cho khách hàng xem danh sách các comment và đánh giá của mọi người")
    @GetMapping("/review/list")
    public ReviewListResponse getReviewList(
            @RequestParam(required = true) Long productId,
            Pageable pageable
    ){
        return reviewService.getListReview(productId,pageable);
    }
}
