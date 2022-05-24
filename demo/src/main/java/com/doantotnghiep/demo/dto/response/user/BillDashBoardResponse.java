package com.doantotnghiep.demo.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDashBoardResponse {
    private Long id;
    private Integer status;
    private Long buyDate;
    private Integer discountPercent;
    private BigDecimal priceTotal;
    private String couponName;
    private String Name;
    private String shipperName;
    private String shipperPhone;
}
