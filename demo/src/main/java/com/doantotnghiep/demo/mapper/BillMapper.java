package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.BillDetailResponse;
import com.doantotnghiep.demo.entity.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {
    public BillDetailResponse toListDTO(Bill bill) {
        BillDetailResponse dto = new BillDetailResponse();
        dto.setId(bill.getId());
        dto.setBuyDate(bill.getBuyDate().getTime());
        dto.setStatus(bill.getStatus());
        dto.setCouponName(bill.getCouponName());
        dto.setDiscountPercent(bill.getDiscountPercent());
        dto.setUserName(bill.getUser().getName());
        dto.setPriceTotal(bill.getPriceTotal());

        return dto;
    }
}
