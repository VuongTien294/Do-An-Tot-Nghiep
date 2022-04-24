package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GenderEnum {
    NAM(0), //bỏ ban người dùng
    NU(1); //ban người dùng
    @Getter
    private final Integer code;
}
