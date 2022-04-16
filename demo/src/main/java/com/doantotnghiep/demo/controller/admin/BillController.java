package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.BillDetailResponse;
import com.doantotnghiep.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping("/member/bill/add")
    public void addBill(
            @RequestBody(required = true) AddBillRequest addBillRequest
    ) {
        billService.addBill(addBillRequest);
    }

    @GetMapping("/admin/bill/list")
    public BillListResponse getCouponList(
            @RequestParam(required = false) String userName,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getListBill(userName,sort,pageable);
    }

    @GetMapping("/admin/bill/delete")
    public void deleteCoupon(
            @RequestParam(required = true) Long id
    ){
        billService.deleteBill(id);
    }

    @GetMapping("/admin/bill")
    public BillDetailResponse getDetailBillAdmin(
            @RequestParam(required = false) Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }

    @GetMapping("/member/bill")
    public BillDetailResponse getDetailBill(
            @RequestParam(required = false) Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }

}
