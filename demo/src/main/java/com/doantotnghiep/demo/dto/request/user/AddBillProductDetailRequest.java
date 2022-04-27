package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

@Data
public class AddBillProductDetailRequest {
    private Long id;
//    private Long unitPrice;
    private Integer quantity;
    private Long productId;
    private Long sizeId;
}
