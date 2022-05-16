package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.BuyRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest2;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
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

//    @ApiOperation("Api cho phép khách hàng đặt hàng(Điều kiện là phải đăng nhập)")
//    @PostMapping("/member/buy")
//    public void buy(
//            @RequestBody BuyRequest buyRequest
//            ){
//        billService.buyProduct(buyRequest);
//    }

    @ApiOperation("Api cho phép khách hàng đặt hàng(Nếu đăng nhập rồi thì truyền user id còn ko thì truyền các thông tin cơ bản của user)")
    @PostMapping("/member/buy")
    public void buy(
            @RequestBody BuyRequest2 buyRequest
    ){
        billService.buyProduct2(buyRequest);
    }

    @ApiOperation("Api cho phép khách hàng xem danh sách hàng đã đặt(Điều kiện là phải đăng nhập)")
    @GetMapping("/member/bill/{userId}")
    public BillListResponse getListBillByUserId(
            @PathVariable Long userId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getListBillForUser(userId, sort,pageable);
    }

    @ApiOperation("Api cho khách hàng lấy chi tiết 1 bill")
    @GetMapping("/member/bill/detail/{billId}")
    public MemberBillDetailResponse getDetailBillUser(
            @PathVariable Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }
}
