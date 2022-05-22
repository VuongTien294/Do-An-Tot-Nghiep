package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddBillRequest {
    private Integer status;
    private Integer discountPercent;
    private BigDecimal priceTotal;
    private String couponName;
    private Long userId;
}
