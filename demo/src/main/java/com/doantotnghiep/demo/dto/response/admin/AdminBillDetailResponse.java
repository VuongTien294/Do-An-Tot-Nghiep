package com.doantotnghiep.demo.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBillDetailResponse {
    private Long id;
    private Integer status;
    private Long buyDate;
    private Integer discountPercent;
    private Long priceTotal;
    private String couponName;
    private String userName;
    private String shipperName;
    private String shipperPhone;
}
