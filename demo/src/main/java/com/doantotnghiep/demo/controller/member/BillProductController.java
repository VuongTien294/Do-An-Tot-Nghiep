package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddBillProductRequest;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.service.BillProductService;
import com.doantotnghiep.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class BillProductController {
    private final BillProductService billProductService;
    private final BillService billService;

    @PostMapping("/member/billproduct/add")
    public void addBillProduct(
            @RequestBody(required = true) AddBillProductRequest addBillRequest
    ) {
        billProductService.addBillProduct(addBillRequest);
    }

    @GetMapping("/member/bill")
    public MemberBillDetailResponse getDetailBill(
            @RequestParam(required = false) Long billId,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return billService.getBillDetail(billId , sort , pageable);
    }
}
