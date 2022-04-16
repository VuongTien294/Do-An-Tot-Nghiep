package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BillStatusEnum {

    PROCESSING(0), //đang xử lí
    SOLD(1), //đã mua
    DONE(2); //đã giao đến

    @Getter
    private final Integer code;

}
