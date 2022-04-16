package com.doantotnghiep.demo.dto.response.admin;

import lombok.Data;

@Data
public class TokenDTO {
    private String accessToken;
    private Long expirationTime;
}
