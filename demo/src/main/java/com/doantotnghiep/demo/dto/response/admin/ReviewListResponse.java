package com.doantotnghiep.demo.dto.response.admin;

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
public class ReviewListResponse {
    @JsonProperty("rows")
    private List<ReviewDetailResponse> list;

    @JsonProperty("total")
    private Long total;
}
