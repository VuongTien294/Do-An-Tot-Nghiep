package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.ChangeBillStatus;
import com.doantotnghiep.demo.dto.request.admin.UpdateShipper;
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

    @ApiOperation("Api cho admin đổi trạng thái của 1 bill.Nếu là cancel bill thì truyền lí do ko thì thôi")
    @PostMapping("/admin/bill/change-status/{billId}")
    public void changeBillStatus(
            @PathVariable Long billId,
            @RequestBody ChangeBillStatus changeBillStatus
    ){
        billService.changeBillStatus(billId , changeBillStatus);
    }

    @ApiOperation("Api cho admin update lại shipper từ tên đến số điện thoại")
    @PutMapping("/admin/bill/update-shipper/{billId}")
    public void changeBillShipper(
            @PathVariable Long billId,
            @RequestBody UpdateShipper updateShipper
    ){
        billService.updateShipper(billId , updateShipper);
    }

}
