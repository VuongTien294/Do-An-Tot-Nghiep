package com.doantotnghiep.demo.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillProductDetailResponse {
    private Long id;
    private Long unitPrice;
    private Integer quantity;
    private String productName;
}
