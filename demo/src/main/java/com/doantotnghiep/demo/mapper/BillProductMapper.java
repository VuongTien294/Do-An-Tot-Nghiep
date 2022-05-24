package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.user.BillDashBoardResponse;
import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import com.doantotnghiep.demo.entity.Bill;
import com.doantotnghiep.demo.entity.BillProduct;
import org.springframework.stereotype.Component;

@Component
public class BillProductMapper {
    public BillProductDetailResponse toListDTO(BillProduct billProduct) {
        BillProductDetailResponse dto = new BillProductDetailResponse();
        dto.setId(billProduct.getId());
        dto.setUnitPrice(billProduct.getUnitPrice());
        dto.setQuantity(billProduct.getQuantity());
        dto.setProductName(billProduct.getProduct().getName());

        return dto;
    }

    public BillDashBoardResponse toDashBoard(Bill bill) {
        BillDashBoardResponse dto = new BillDashBoardResponse();
        dto.setId(bill.getId());
        dto.setPriceTotal(bill.getPriceTotal());
        return dto;
    }
}
