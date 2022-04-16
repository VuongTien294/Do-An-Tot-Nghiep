package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddBillProductRequest;
import com.doantotnghiep.demo.service.BillProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class BillProductController {
    private final BillProductService billProductService;

    @PostMapping("/member/billproduct/add")
    public void addBillProduct(
            @RequestBody(required = true) AddBillProductRequest addBillRequest
    ) {
        billProductService.addBillProduct(addBillRequest);
    }
}
