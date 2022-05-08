package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddIntroRequest;
import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.service.IntroService;
import com.doantotnghiep.demo.service.impl.CoudinaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminIntroController {
    private final CoudinaryService cloudinaryGifService;
    private final IntroService introService;

    @ApiOperation("Api trả về link ảnh cho FE")
    @PostMapping("/admin/intro/video")
    public String upLoad(@RequestParam("file") MultipartFile gifFile){
        return cloudinaryGifService.uploadVideo(gifFile);
    }

    @ApiOperation("Api cho admin tạo mới 1 intro")
    @PostMapping("/admin/intro/add")
    public void addIntro(
            @RequestBody(required = true) AddIntroRequest addIntroRequest
    ) {
        introService.addIntro(addIntroRequest);
    }


}
