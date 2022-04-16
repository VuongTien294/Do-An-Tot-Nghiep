package com.doantotnghiep.demo.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailResponse {
    private Long id;
    private Integer status;
    private Long buyDate;
    private Integer discountPercent;
    private Long priceTotal;
    private String couponName;
    private String userName;

    @JsonProperty("rows")
    private List<BillProductDetailResponse> list;

    @JsonProperty("total")
    private Long total;

}
