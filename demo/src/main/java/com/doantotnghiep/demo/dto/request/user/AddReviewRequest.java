package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

@Data
public class AddReviewRequest {
    private Long userId;
    private Long star;
    private String comment;
    private Long productId;
}
