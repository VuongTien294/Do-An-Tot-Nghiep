package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

import java.util.List;

@Data
public class BuyRequest2 {
    private Integer discountPersent;
    private String couponName;
    private Long userId;
    private Long priceTotal;

    private List<AddBillProductDetailRequest> listBillProducts;
}
