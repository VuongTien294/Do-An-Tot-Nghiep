package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductGenderEnum {
    Male(0),
    Female(1);

    @Getter
    private final Integer code;
}
