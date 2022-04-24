package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.admin.AddSizeRequest;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;

import java.util.List;

public interface SizeService {
    void addSize(AddSizeRequest addSizeRequest);

//    void modifiedSize(AddSizeRequest addSizeRequest);

    void deleteSize(Long sizeId);

    List<SizeDetailResponse> listSizeByProductId(Long productId);
}
