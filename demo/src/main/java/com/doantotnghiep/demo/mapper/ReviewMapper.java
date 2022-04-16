package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.ReviewDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.entity.Review;
import com.doantotnghiep.demo.entity.Size;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDetailResponse toListDTO(Review review) {
        ReviewDetailResponse dto = new ReviewDetailResponse();
        dto.setId(review.getId());
        dto.setUserName(review.getUser().getName());
        dto.setComment(review.getComment());
        dto.setStar(review.getStar());

        return dto;
    }
}
