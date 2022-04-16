package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MaterialEnum {
    Leather(0),
    Suede(1);

    @Getter
    private final Integer code;
}
