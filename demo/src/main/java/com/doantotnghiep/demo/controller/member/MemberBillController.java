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

    @ApiOperation("Api cho phép khách hàng đặt hàng(Nếu truyền user id thì tạo 1 bill gắn với user đó và update lại theo FE truyền vào.Nếu ko thì tạo 1 user mới có username = guest,password = 1)")
    @PostMapping("/buy")
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

    @ApiOperation("Api cho phép khách hàng xem danh sách hàng đã đặt(Không cần đăng nhập.Chỉ cần truyền vào phone)")
    @GetMapping("/bill/{phone}")
    public BillListResponse getListBillByPhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getListBillByPhone(phone, sort,pageable);
    }

    @ApiOperation("Api cho khách hàng lấy chi tiết 1 bill")
    @GetMapping("/bill/detail/{billId}")
    public MemberBillDetailResponse getDetailBillUser(
            @PathVariable Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }

    @ApiOperation("Api cho khách hàng lấy chi tiết 1 bill")
    @GetMapping("/bill/export-pdf/{billId}")
    public void getExportBillUser(
            @PathVariable Long billId
    ){
        billService.genPDF(billId);
    }

}
