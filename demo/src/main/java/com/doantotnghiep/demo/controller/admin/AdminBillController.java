package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.service.BillService;
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
public class AdminBillController {
    private final BillService billService;

    @ApiOperation("Api cho admin lấy danh sách các bill")
    @GetMapping("/admin/bill/list")
    public BillListResponse getBillList(
            @RequestParam(required = false) String userName,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getListBill(userName,sort,pageable);
    }

    @ApiOperation("Api cho admin xóa 1 bill")
    @GetMapping("/admin/bill/delete")
    public void deleteBill(
            @RequestParam(required = true) Long id
    ){
        billService.deleteBill(id);
    }

    @ApiOperation("Api cho admin lấy chi tiết 1 bill")
    @GetMapping("/admin/bill/{billId}")
    public MemberBillDetailResponse getDetailBillAdmin(
            @PathVariable Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }

    @ApiOperation("Api cho admin đổi trạng thái của 1 bill")
    @GetMapping("/admin/bill/change-status/{billId}")
    public void changeBillStatus(
            @PathVariable Long billId,
            @RequestParam Integer billStatus
    ){
        billService.changeBillStatus(billId , billStatus);
    }



}
