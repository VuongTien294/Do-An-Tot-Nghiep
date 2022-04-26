package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.service.CouponService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberCouponController {
    private final CouponService couponService;

    @ApiOperation("Api cho phép khách hàng nhập mã giảm giá để trừ tiền.Nếu thành công thì trả về dữ liệu.Nếu sai trả về null")
    @GetMapping("/coupon/{code}")
    public CouponDetailResponse getProgetCouponByCode(
            @PathVariable(required = true) String code
    ) {
        return couponService.getCouponByCode(code);
    }
}
