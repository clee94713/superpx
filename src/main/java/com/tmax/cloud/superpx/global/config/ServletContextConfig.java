package com.tmax.cloud.superpx.global.config;

import com.tmax.cloud.superpx.global.interceptor.ApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ServletContextConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("JWT-TOKEN");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // ���ͼ��� ���
        registry.addInterceptor(apiInterceptor())
                .addPathPatterns("/**") // Interceptor�� ����� ���
                .excludePathPatterns("/v3/api-docs")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/csrf")
                .excludePathPatterns("/error**"); // Interceptor�� ������� ���� ���
    }

    @Bean
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }
}