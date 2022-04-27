package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.response.admin.DashBoardBodyResponse;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface DashBoardService {
    Map<String,Long> getHeaderDashBoard();

    DashBoardBodyResponse getListDashBoard(Integer month, Integer year, Integer sortBy, Pageable pageable);
}
