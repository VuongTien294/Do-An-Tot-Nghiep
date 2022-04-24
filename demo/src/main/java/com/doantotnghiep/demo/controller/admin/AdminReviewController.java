package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.service.BillService;
import com.doantotnghiep.demo.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminReviewController {
    private final ReviewService reviewService;

    @ApiOperation("Api cho admin xóa 1 review")
    @GetMapping("/admin/review/delete")
    public void deleteReview(
            @RequestParam(required = true) Long id
    ){
        reviewService.deleteReview(id);
    }

    @ApiOperation("Api cho admin xem danh sách review của 1 sản phẩm")
    @GetMapping("/admin/review/list")
    public void deleteReview(
            @RequestParam(required = true) Long productId,
            Pageable pageable
    ){
        reviewService.getListReview(productId , pageable);
    }

}
