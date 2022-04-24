package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BillStatusEnum {

    CHUA_XU_LY(0),
    DA_XU_LY(1),
    DANG_GIAO(2),
    DA_NHAN(3),
    DA_HUY(4),
    ;

    @Getter
    private final Integer code;


}
