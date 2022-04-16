package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
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
public class UserController {

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

    //them user
    @PostMapping("/admin/user/add")
    public void addUser(
            @RequestBody(required = true) AddUserRequest userRequest
    ) {
        userService.addUser(userRequest);
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

    //chinh sau user
    @PutMapping("/admin/user/modified")
    public void modifiedUser(
            @RequestBody(required = true) AddUserRequest userRequest
    ) {
        userService.modifiedUser(userRequest);
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

    //chỉnh sửa password cho admin
    @GetMapping("/user/change-password")
    public UserDetailResponse modifiedPassword(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String oldPassword,
            @RequestParam(required = true) String newPassword
    ){
        return userService.modifiedPassword(username,oldPassword,newPassword);
    }

    @GetMapping(value = "/member/me")
    private UserDetailResponse me() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.getUserDetail(currentUser.getId());
    }


}
