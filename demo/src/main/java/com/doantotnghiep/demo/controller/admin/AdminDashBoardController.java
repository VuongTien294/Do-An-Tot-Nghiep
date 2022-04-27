package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.response.admin.DashBoardBodyResponse;
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

    @ApiOperation("Api cho admin lấy header của dashboard(là 4 ô to của màn dashboard)")
    @GetMapping("/admin/dashboard/header")
    public Map<String,Long> getHeaderDashBoardAdmin(
    ){
        return dashBoardService.getHeaderDashBoard();
    }

    @ApiOperation("Api cho admin lấy header của dashboard(là 4 ô to của màn dashboard)")
    @GetMapping("/admin/dashboard/body")
    public DashBoardBodyResponse getBodyDashBoardAdmin(
            @RequestParam(required = true) Integer month,
            @RequestParam(required = true) Integer year,
            @RequestParam(required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return dashBoardService.getListDashBoard(month,year,sort,pageable);
    }

}
