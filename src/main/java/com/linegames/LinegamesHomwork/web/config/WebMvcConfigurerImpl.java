package com.linegames.LinegamesHomwork.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMmvc 관련 설정 클래스
 */
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

    /**
     * Resource 파일 핸들러 관련 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
