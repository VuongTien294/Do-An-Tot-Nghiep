package com.doantotnghiep.demo.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "dj2rsasts");
        config.put("api_key", "771457353652775");
        config.put("api_secret", "86TjUKKgobOVQiJtKAh7UFi9LyA");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
