package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BanTypeEnum {
    UN_BAN(0), //bỏ ban người dùng
    BAN(1); //ban người dùng
    @Getter
    private final Integer code;
}
