package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

import java.util.List;

@Data
public class AddUserRequest {
    private Long id;
    private String name;
    private Integer age;
    private List<String> roles;
    private String username;
    private String password;
    private String address;
    private String gender;
    private String phone;
    private String email;
}
