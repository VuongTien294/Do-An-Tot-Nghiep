package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BranchEnum {
    Nike(0),
    Adidas(1),
    Merrel(2),
    Gucci(3),
    Skechers(4);

    @Getter
    private final Integer code;
}
