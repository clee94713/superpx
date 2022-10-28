package com.tmax.cloud.superpx.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationCommonConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
