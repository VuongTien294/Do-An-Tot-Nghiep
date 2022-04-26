package com.doantotnghiep.demo.dto.response.admin;

import com.doantotnghiep.demo.dto.request.admin.AddSizeRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailResponse implements Serializable {
    private Long id;

    private String name;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("total_quantity")
    private Integer totalQuantity;

    @JsonProperty("sold_quantity")
    private Integer soldQuantity;

    @JsonProperty("description")
    private String description;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("total_rating")
    private Integer totalRating;

    @JsonProperty("total_star")
    private Long totalStar;

    @JsonProperty("image")
    private String image;

    private Integer gender;

    private Integer branch;

    private Integer style;

    private Integer color;

    private Integer material;

    private Integer technology;

    @JsonProperty("listSize")
    private List<SizeDetailResponse> sizeDetailResponses;

}
