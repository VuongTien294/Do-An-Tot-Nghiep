package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddCouponRequest;
import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import com.doantotnghiep.demo.service.CouponService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminCouponController {
    private final CouponService couponService;

    @ApiOperation("Api cho admin thêm 1 coupon")
    @PostMapping("/admin/coupon/add")
    public void addCoupon(
            @RequestBody(required = true) AddCouponRequest addCouponRequest
    ) {
        couponService.addCoupon(addCouponRequest);
    }

    @ApiOperation("Api cho admin lấy danh sách các coupon")
    @GetMapping("/admin/coupon/list")
    public CouponListResponse getCouponList(
            @RequestParam(required = false) String couponName,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return couponService.getListCoupon(couponName,sort,pageable);
    }

    @ApiOperation("Api cho admin sửa 1 coupon")
    @PutMapping("/admin/coupon/modified")
    public void modifiedCoupon(
            @RequestBody(required = true) AddCouponRequest addCouponRequest
    ) {
        couponService.modifiedCoupon(addCouponRequest);
    }

    @ApiOperation("Api cho admin xóa 1 coupon")
    @GetMapping("/admin/coupon/delete")
    public void deleteCoupon(
            @RequestParam(required = true) Long id
    ){
        couponService.deleteCoupon(id);
    }

}
