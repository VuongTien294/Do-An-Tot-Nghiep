package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDetailResponse toListDTO(User user) {
        UserDetailResponse dto = new UserDetailResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUserName(user.getUsername());
        dto.setAddress(user.getAddress());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setPhone(user.getPhone());
        dto.setRoles(user.getRoles());
        dto.setEnabled(user.getEnabled());

        return dto;
    }
}
