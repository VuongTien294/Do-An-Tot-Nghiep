package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.entity.Size;
import com.doantotnghiep.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SizeMapper {
    public SizeDetailResponse toListDTO(Size size) {
        SizeDetailResponse dto = new SizeDetailResponse();
        dto.setId(size.getId());
        dto.setName(size.getName());
        dto.setQuantity(size.getQuantity());

        return dto;
    }
}
