package com.doantotnghiep.demo.dto.request.admin;

import lombok.Data;

@Data
public class ChangeBillStatus {
    private Integer billStatus;
    private String reasonCancel;
}
