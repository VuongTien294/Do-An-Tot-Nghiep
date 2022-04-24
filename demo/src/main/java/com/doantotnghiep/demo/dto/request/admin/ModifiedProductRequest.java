package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

import java.util.List;

@Data
public class ModifiedProductRequest {
    private String name;
    private Long price;
    private String description;
    private String manufacturer;
    private String image;
    private Integer gender;
    private Integer branch;
    private Integer style;
    private Integer color;
    private Integer material;
    private Integer technology;

    private List<AddSizeRequest> listSize;
}
