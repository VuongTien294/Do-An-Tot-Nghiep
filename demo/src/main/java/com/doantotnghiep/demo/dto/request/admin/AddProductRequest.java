package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private Long price;
    private String description; //mo ta dai
    private String manufacturer; //mo ta ngan
    private String image;
    private Integer gender;
    private Integer branch;
    private Integer style;
    private Integer color;
    private Integer material;
    private Integer technology;
}
