package com.doantotnghiep.demo.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse implements Serializable {

    @JsonProperty("rows")
    private List<ProductDetailResponse> list;

    @JsonProperty("total")
    private Long total;

}
