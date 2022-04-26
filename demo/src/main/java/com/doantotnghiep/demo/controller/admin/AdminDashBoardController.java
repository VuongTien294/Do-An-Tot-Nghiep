package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.service.DashBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminDashBoardController {
    private final DashBoardService dashBoardService;

    @ApiOperation("Api cho admin lấy header của dashboard(là 4 ô to to của màn dashboard ý bạn)")
    @GetMapping("/admin/dashboard/header")
    public Map<String,Long> getHeaderDashBoardAdmin(
    ){
        return dashBoardService.getHeaderDashBoard();
    }

}
