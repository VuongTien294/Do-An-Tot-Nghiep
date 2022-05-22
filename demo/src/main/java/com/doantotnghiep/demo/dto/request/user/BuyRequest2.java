package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BuyRequest2 {
    private Integer discountPersent;
    private String couponName;
    private Long userId;
    private BigDecimal priceTotal;

    private List<AddBillProductDetailRequest> listBillProducts;

    //Khi ko có user trong db
    private String name;
    private Integer age;
//    private String username;
//    private String password;
    private String address;
    private String gender;
    private String phone;
    private String email;
//
//    private Integer billType;

}
