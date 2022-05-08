package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedUser;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberAccountController {
    private final UserService userService;

    //login,đổi mk đã có trong class AdminUserController


    //Đăng kí user
    @ApiOperation("Api đăng kí tài khoản cho cả admin và user")
    @PostMapping("/user/add")
    public void addUser(
            @RequestBody(required = true) AddUserRequest userRequest
    ) {
        userService.addUser(userRequest);
    }

    @ApiOperation("Api chỉnh sửa thông tin khách hàng cho khách hàng")
    @PutMapping("/member/user/modified")
    public void modifiedUser(
            @RequestBody(required = true) ModifiedUser modifiedUser
    ) {
        userService.modifiedUser(modifiedUser);
    }

    @ApiOperation("Api xem chi tiết 1 user")
    @GetMapping("/user/{id}")
    public UserDetailResponse getUserDetail(
            @PathVariable(required = true) Long id
    ){
        return userService.getUserDetail(id);
    }

}
