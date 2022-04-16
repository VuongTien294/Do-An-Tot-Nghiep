package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StyleEnum {
    SlipOns(0),
    Boots(1),
    Sandals(2),
    LaceUps(3),
    Oxfords(4);

    @Getter
    private final Integer code;
}
