package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedUser;
import com.doantotnghiep.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final UserService userService;

    //Đăng kí user
    @PostMapping("/user/add")
    public void addUser(
            @RequestBody(required = true) AddUserRequest userRequest
    ) {
        userService.addUser(userRequest);
    }

    @PutMapping("/user/modified")
    public void modifiedUser(
            @RequestBody(required = true) ModifiedUser modifiedUser
    ) {
        userService.modifiedUser(modifiedUser);
    }
}
