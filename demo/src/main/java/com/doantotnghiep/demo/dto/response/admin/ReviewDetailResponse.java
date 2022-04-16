package com.doantotnghiep.demo.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDetailResponse implements Serializable {
    private Long id;
    private String userName;
    private Long star;
    private String comment;
    private Long createdDate;
}
