package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;
@Data
public class AddSizeRequest {
    private Long id;
    private String name;
    private Integer quantity;
    private Long productId;
}
