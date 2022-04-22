package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedUser;
import com.doantotnghiep.demo.dto.response.admin.TokenDTO;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.UserListResponse;
import com.doantotnghiep.demo.dto.response.user.UserPrincipal;
import com.doantotnghiep.demo.security.JwtCustomException;
import com.doantotnghiep.demo.security.JwtTokenProvider;
import com.doantotnghiep.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public TokenDTO login(@RequestParam(required = true, name = "username") String username,
                          @RequestParam(required = true, name = "password") String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("/////////////////////////////////////////");
            return jwtTokenProvider.createToken(username);
        } catch (Exception e) {
            throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //chỉnh sửa thông tin cá nhân
    @PutMapping("/admin/user/modified")
    public void modifiedUser(
            @RequestBody(required = true) ModifiedUser modifiedUser
    ) {
        userService.modifiedUser(modifiedUser);
    }

    //chỉnh sửa password
    @GetMapping("/user/change-password")
    public UserDetailResponse modifiedPassword(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String oldPassword,
            @RequestParam(required = true) String newPassword
    ){
        return userService.modifiedPassword(username,oldPassword,newPassword);
    }

    //xem list user
    @GetMapping("/admin/user/list")
    public UserListResponse getUserList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return userService.getListUser(search,sort,pageable);
    }

    //lay chi tiet user
    @GetMapping("/admin/user/{id}")
    public UserDetailResponse getUserDetail(
            @PathVariable(required = true) Long id
    ){
        return userService.getUserDetail(id);
    }

    //xoa user
    @GetMapping("/admin/user/delete")
    public void deleteUser(
            @RequestParam(required = true) Long id
    ){
        userService.deleteUser(id);
    }

    //api lay chi tiết user bằng assetToken
    @GetMapping(value = "/member/me")
    private UserDetailResponse me() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.getUserDetail(currentUser.getId());
    }

    //Thay đổi quyền của tài khoản
    @GetMapping(value = "/admin/user/change-role")
    private UserDetailResponse changeRole(
            @RequestParam(required = true) Long userId,
            @RequestParam(required = true) Integer roleEnum
    ) {
        return userService.changeRole(userId , roleEnum);
    }

    //Ban user
    @GetMapping(value = "/admin/user/ban-user")
    private UserDetailResponse banUser(
            @RequestParam(required = true) Long userId,
            @RequestParam(required = true) Integer banEnum
    ) {
        return userService.banUser(userId , banEnum);
    }
}
