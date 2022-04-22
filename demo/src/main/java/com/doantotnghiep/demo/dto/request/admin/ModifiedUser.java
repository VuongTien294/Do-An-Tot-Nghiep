package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

@Data
public class ModifiedUser {
    private Long id;
    private String name;
    private Integer age;
    private String username;
    private String address;
    private String gender;
    private String phone;
    private String email;
}
