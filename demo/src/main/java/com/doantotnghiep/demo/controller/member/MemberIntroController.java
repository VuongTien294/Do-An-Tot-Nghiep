package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.service.IntroService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberIntroController {
    private final IntroService introService;

    @ApiOperation("Api xem chi tiết 1 intro")
    @GetMapping("/intro")
    public Map<String,String> getIntro(
    ){
         return introService.getIntro();
    }
}
