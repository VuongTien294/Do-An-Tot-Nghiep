package com.doantotnghiep.demo.ultil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TechnologyEnum {
    BioBevel(0),
    Groove(1),
    FlexBevel(2);

    @Getter
    private final Integer code;
}
