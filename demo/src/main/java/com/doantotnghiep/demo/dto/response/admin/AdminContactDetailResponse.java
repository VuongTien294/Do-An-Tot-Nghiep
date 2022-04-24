package com.doantotnghiep.demo.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminContactDetailResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String message;
}
