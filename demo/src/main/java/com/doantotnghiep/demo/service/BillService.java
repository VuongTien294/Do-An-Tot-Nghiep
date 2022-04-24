package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.response.admin.AdminBillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import org.springframework.data.domain.Pageable;

public interface BillService {
    void addBill(AddBillRequest addBillRequest);

    BillListResponse getListBill(String userName, Integer sortBy, Pageable pageable);

    MemberBillDetailResponse getBillDetail(Long billId, Integer sortBy, Pageable pageable);

    void deleteBill(Long billId);

    void changeBillStatus(Long billId, Integer billStatus);

}
