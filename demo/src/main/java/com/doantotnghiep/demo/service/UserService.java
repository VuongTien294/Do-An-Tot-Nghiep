package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.UserListResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    //dang ki user cho admin
    void addUser(AddUserRequest addUserRequest);

    void modifiedUser(AddUserRequest addUserRequest);

    UserDetailResponse getUserDetail(Long id);

    UserListResponse getListUser(String search, Integer sortBy, Pageable pageable);

    UserDetailResponse modifiedPassword(String username, String password, String newPassword);

    void deleteUser(Long id);
}
