package com.doantotnghiep.demo.dto.response.pdf;

import lombok.Data;

@Data
public class BillProductInfo {
    private String productName;
    private Integer quantity;
    private Long unitPrice;
    private Long total;
    private String sizeName;
}
