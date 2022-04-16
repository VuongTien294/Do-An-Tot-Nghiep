package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

import java.util.List;

@Data
public class AddBillProductRequest {
    private List<AddBillProductDetailRequest> list;
}
