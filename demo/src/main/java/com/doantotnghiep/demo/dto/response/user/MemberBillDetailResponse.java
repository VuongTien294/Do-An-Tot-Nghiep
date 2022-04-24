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
public class MemberBillDetailResponse {
    private Long id;
    private Integer status;
    private Long buyDate;
    private Integer discountPercent;
    private Long priceTotal;
    private String couponName;
    private String userName;
    @JsonProperty("total")
    private Long total;

    @JsonProperty("rows")
    private List<BillProductDetailResponse> list;



}
