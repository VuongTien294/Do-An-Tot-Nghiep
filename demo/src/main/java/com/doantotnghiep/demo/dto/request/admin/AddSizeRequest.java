package com.doantotnghiep.demo.dto.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddSizeRequest {
    private Long id;
    private String name;
    private Integer quantity;
    private Long productId;
}
