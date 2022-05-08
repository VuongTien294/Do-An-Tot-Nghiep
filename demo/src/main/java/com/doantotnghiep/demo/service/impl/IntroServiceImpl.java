package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddIntroRequest;
import com.doantotnghiep.demo.entity.Intro;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.repository.IntroRepository;
import com.doantotnghiep.demo.service.IntroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IntroServiceImpl implements IntroService {
    private final IntroRepository introRepository;

    @Override
    public void addIntro(AddIntroRequest addIntroRequest){
        Intro intro = Intro.builder()
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .subject(addIntroRequest.getSubject())
                .content(addIntroRequest.getContent())
                .video(addIntroRequest.getVideo())
                .isDeleted(false)
                .build();

        introRepository.save(intro);

    }

    @Override
    public Map<String,String> getIntro(){
        Intro intro = introRepository.findAll().get(0);

        Map<String,String> map = new HashMap<>();
        map.put("subject" , intro.getSubject());
        map.put("content" , intro.getContent());
        map.put("video" , intro.getVideo());
        return  map;
    }
}
