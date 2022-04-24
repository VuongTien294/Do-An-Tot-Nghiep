package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddSizeRequest;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.service.SizeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminSizeController {
    private final SizeService sizeService;

    @ApiOperation("Api cho admin tạo mới 1 size cho product")
    @PostMapping("/admin/size/add")
    public void addSize(
            @RequestBody(required = true) AddSizeRequest addSizeRequest
    ) {
        sizeService.addSize(addSizeRequest);
    }

    @ApiOperation("Api cho admin xóa 1 size của product")
    @GetMapping("/admin/size/delete")
    public void deleteSize(
            @RequestParam(required = true) Long id
    ){
        sizeService.deleteSize(id);
    }
}
