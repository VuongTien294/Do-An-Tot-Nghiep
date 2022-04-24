package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddSizeRequest;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.service.SizeService;
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

    @PostMapping("/admin/size/add")
    public void addSize(
            @RequestBody(required = true) AddSizeRequest addSizeRequest
    ) {
        sizeService.addSize(addSizeRequest);
    }

    @GetMapping("/admin/size/list")
    public List<SizeDetailResponse> getSizeList(
            @RequestParam(required = true) Long productId
    ){
        return sizeService.listSizeByProductId(productId);
    }

//    @PutMapping("/size/modified")
//    public void modifiedSize(
//            @RequestBody(required = true) AddSizeRequest addSizeRequest
//    ) {
//        sizeService.modifiedSize(addSizeRequest);
//    }

    @GetMapping("/admin/size/delete")
    public void deleteSize(
            @RequestParam(required = true) Long id
    ){
        sizeService.deleteSize(id);
    }


}
