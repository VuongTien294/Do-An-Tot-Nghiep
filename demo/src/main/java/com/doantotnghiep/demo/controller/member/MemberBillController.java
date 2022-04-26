package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddBillProductRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.service.BillProductService;
import com.doantotnghiep.demo.service.BillService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberBillController {
    private final BillService billService;

    @ApiOperation("Api cho phép khách hàng đặt hàng(Điều kiện là phải đăng nhập)")
    @PostMapping("/member/buy")
    public void buy(
            @RequestBody BuyRequest buyRequest
            ){
        billService.buyProduct(buyRequest);
    }

    @ApiOperation("Api cho phép khách hàng xem danh sách hàng đã đặt(Điều kiện là phải đăng nhập)")
    @PostMapping("/member/bill/{userId}")
    public BillListResponse getListBillByUserId(
            @PathVariable Long userId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getListBillForUser(userId, sort,pageable);
    }
}
