package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedUser;
import com.doantotnghiep.demo.dto.response.admin.TokenDTO;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.UserListResponse;
import com.doantotnghiep.demo.dto.response.user.UserPrincipal;
import com.doantotnghiep.demo.entity.User;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.security.JwtCustomException;
import com.doantotnghiep.demo.security.JwtTokenProvider;
import com.doantotnghiep.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
    private final UserRepository userRepository;

    @ApiOperation("Api login cho cả admin và user")
    @PostMapping("/login")
    public TokenDTO login(@RequestParam(required = true, name = "username") String username,
                          @RequestParam(required = true, name = "password") String password) {
        try {


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(Objects.nonNull(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).getPrincipal())){
                UserPrincipal currentUser = (UserPrincipal) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).getPrincipal();
                User user = userRepository.findById(currentUser.getId()).get();
                return jwtTokenProvider.createToken(username ,user);
            }

            return null;

        } catch (Exception e) {
            throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //chỉnh sửa thông tin cá nhân
    @ApiOperation("Api chỉnh sửa thông tin cá nhân cho admin")
    @PutMapping("/admin/user/modified")
    public void modifiedUser(
            @RequestBody(required = true) ModifiedUser modifiedUser
    ) {
        userService.modifiedUser(modifiedUser);
    }

    //chỉnh sửa password
    @ApiOperation("Api đổi password cho cả admin và user")
    @GetMapping("/user/change-password")
    public UserDetailResponse modifiedPassword(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String oldPassword,
            @RequestParam(required = true) String newPassword
    ){
        return userService.modifiedPassword(username,oldPassword,newPassword);
    }

    //xem list user
    @ApiOperation("Api xem list user cho admin")
    @GetMapping("/admin/user/list")
    public UserListResponse getUserList(
            @RequestParam(required = false) String search,
            @RequestParam(required = false,defaultValue = "0") Integer sort,
            Pageable pageable
    ){
        return userService.getListUser(search,sort,pageable);
    }

    //lay chi tiet user
    @ApiOperation("Api xem chi tiết 1 user cho admin")
    @GetMapping("/admin/user/{id}")
    public UserDetailResponse getUserDetail(
            @PathVariable(required = true) Long id
    ){
        return userService.getUserDetail(id);
    }

    //xoa user
    @GetMapping("/admin/user/delete")
    @ApiOperation("Api xóa 1 user cho admin")
    public void deleteUser(
            @RequestParam(required = true) Long id
    ){
        userService.deleteUser(id);
    }

    //api lay chi tiết user bằng assetToken
    @ApiOperation("Api để lấy thông tin 1 user bằng assetToken.FE sau khi đang nhập thành công thì gọi api này để lấy chi tiết user sau đó lưu vào localStorage")
    @GetMapping(value = "/member/me")
    private UserDetailResponse me() {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        log.info("///////////////// User Principal : {} "+currentUser);
        return userService.getUserDetail(currentUser.getId());
    }

    //Thay đổi quyền của tài khoản
    @ApiOperation("Api đổi quyền cho user thành admin hoặc member")
    @GetMapping(value = "/admin/user/change-role")
    private UserDetailResponse changeRole(
            @RequestParam(required = true) Long userId,
            @RequestParam(required = true) Integer roleEnum
    ) {
        return userService.changeRole(userId , roleEnum);
    }

    //Ban user
    @ApiOperation("Api cấm user.Nếu 1 user bị cấm thì khi gọi api login sẽ bị trả về lỗi 500")
    @GetMapping(value = "/admin/user/ban-user")
    private UserDetailResponse banUser(
            @RequestParam(required = true) Long userId,
            @RequestParam(required = true) Integer banEnum
    ) {
        return userService.banUser(userId , banEnum);
    }
}
