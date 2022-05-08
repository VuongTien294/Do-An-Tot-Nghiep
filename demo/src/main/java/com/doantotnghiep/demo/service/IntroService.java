package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.admin.AddIntroRequest;

import java.util.Map;

public interface IntroService {
    void addIntro(AddIntroRequest addIntroRequest);

    Map<String,String> getIntro();
}
