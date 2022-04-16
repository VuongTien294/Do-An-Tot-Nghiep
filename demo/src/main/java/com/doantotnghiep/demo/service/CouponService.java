package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.admin.AddCouponRequest;
import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import org.springframework.data.domain.Pageable;

public interface CouponService {
    void addCoupon(AddCouponRequest addCouponRequest);

    void modifiedCoupon(AddCouponRequest addCouponRequest);

    void deleteCoupon(Long couponId);

    CouponDetailResponse getCouponByCode(String code);

    CouponListResponse getListCoupon(String couponName, Integer sortBy, Pageable pageable);
}
