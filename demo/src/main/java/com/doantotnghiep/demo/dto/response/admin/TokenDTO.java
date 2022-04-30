package com.doantotnghiep.demo.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDTO {
    private String accessToken;
    private Long expirationTime;
    private Long userId;
    private Long name;
}
