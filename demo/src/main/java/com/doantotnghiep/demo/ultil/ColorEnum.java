package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ColorEnum {
    Black(0),
    White(1),
    Blue(2),
    Red(3),
    Green(4),
    Grey(5),
    Orange(6),
    Cream(7),
    Brown(8);

    @Getter
    private final Integer code;
}
