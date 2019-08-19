package com.lrowy.config;

import com.lrowy.interceptor.LoginInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfigurer extends WebMvcConfigurerAdapter {
    @Value("${web.upload-path}")
    private String uploadPath;

    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(10485760);
        multipartResolver.setMaxInMemorySize(4096);
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/upload/**")) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uploadPath);
        }
        super.addResourceHandlers(registry);
    }
}

