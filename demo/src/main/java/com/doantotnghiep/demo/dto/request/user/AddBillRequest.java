package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;
@Data
public class AddBillRequest {
    private Integer status;
    private Integer discountPercent;
    private Long priceTotal;
    private String couponName;
    private Long userId;
}
