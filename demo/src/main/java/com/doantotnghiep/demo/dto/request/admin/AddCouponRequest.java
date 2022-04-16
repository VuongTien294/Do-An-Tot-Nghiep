package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

@Data
public class AddCouponRequest {
    private Long id;
    private String name;
    private String code;
    private Integer percent;
}
