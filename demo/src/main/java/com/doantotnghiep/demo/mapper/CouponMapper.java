package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.entity.Coupon;
import com.doantotnghiep.demo.entity.Size;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {
    public CouponDetailResponse toListDTO(Coupon coupon) {
        CouponDetailResponse dto = new CouponDetailResponse();
        dto.setId(coupon.getId());
        dto.setName(coupon.getName());
        dto.setCode(coupon.getCode());
        dto.setPercent(coupon.getPercent());

        return dto;
    }
}
