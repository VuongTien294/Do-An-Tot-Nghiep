package com.doantotnghiep.demo.dto.response.admin;

import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import java.util.List;

@Data
public class DashBoardBodyResponse {
    private Long soldQuantity;
    private Long totalPrice;

    private List<BillProductDetailResponse> list;

}
