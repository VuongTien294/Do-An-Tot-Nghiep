package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.admin.ChangeBillStatus;
import com.doantotnghiep.demo.dto.request.admin.UpdateShipper;
import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest2;
import com.doantotnghiep.demo.dto.response.admin.AdminBillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.admin.DashBoardBodyResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import org.springframework.data.domain.Pageable;

public interface BillService {
    void addBill(AddBillRequest addBillRequest);

    void buyProduct(BuyRequest buyRequest);

    void buyProduct2(BuyRequest2 buyRequest);

    BillListResponse getListBill(String userName, Integer sortBy, Pageable pageable);

    MemberBillDetailResponse getBillDetail(Long billId, Integer sortBy, Pageable pageable);

    void deleteBill(Long billId);

    void changeBillStatus(Long billId, ChangeBillStatus changeBillStatus);

    BillListResponse getListBillForUser(Long userId, Integer sortBy, Pageable pageable);

    void updateShipper(Long billId, UpdateShipper updateShipper);

    BillListResponse getListBillByPhone(String phone, Integer sortBy, Pageable pageable);

    void genPDF(Long billId);
}
