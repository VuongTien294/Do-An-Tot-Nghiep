package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.response.admin.BillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import org.springframework.data.domain.Pageable;

public interface BillService {
    void addBill(AddBillRequest addBillRequest);

    BillListResponse getListBill(String userName, Integer sortBy, Pageable pageable);

    com.doantotnghiep.demo.dto.response.user.BillDetailResponse getBillDetail(Long billId, Integer sortBy, Pageable pageable);

    void deleteBill(Long billId);

    BillDetailResponse billDetail(Long billId);
}
