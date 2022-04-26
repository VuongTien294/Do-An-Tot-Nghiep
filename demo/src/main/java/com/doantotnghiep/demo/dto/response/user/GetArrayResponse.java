package com.doantotnghiep.demo.dto.response.user;

import lombok.Data;

import java.util.List;

@Data
public class GetArrayResponse<T> {
    private long total;
    private List<T> rows;

}

