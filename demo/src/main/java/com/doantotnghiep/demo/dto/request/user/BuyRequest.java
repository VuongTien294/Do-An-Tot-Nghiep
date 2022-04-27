package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;
import java.util.List;

@Data
public class BuyRequest {
    private Integer discountPersent;
    private String couponName;
    private Long userId;

    private List<AddBillProductDetailRequest> listBillProducts;
}
