package com.doantotnghiep.demo.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponListResponse implements Serializable {
    @JsonProperty("rows")
    private List<CouponDetailResponse> list;

    @JsonProperty("total")
    private Long total;
}
