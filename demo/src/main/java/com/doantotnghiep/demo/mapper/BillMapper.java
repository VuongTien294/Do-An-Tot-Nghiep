package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.AdminBillDetailResponse;
import com.doantotnghiep.demo.entity.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {
    public AdminBillDetailResponse toListDTO(Bill bill) {
        AdminBillDetailResponse dto = new AdminBillDetailResponse();
        dto.setId(bill.getId());
        dto.setBuyDate(bill.getBuyDate().getTime());
        dto.setStatus(bill.getStatus());
        dto.setCouponName(bill.getCouponName());
        dto.setDiscountPercent(bill.getDiscountPercent());
        dto.setUserName(bill.getUser().getName());
        dto.setPriceTotal(bill.getPriceTotal());

        if(bill.getShipperName() != null){
            dto.setShipperName(bill.getShipperName());
        }

        if(bill.getShipperName() != null){
            dto.setShipperPhone(bill.getShipperPhone());
        }
        return dto;
    }
}
