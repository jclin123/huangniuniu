package com.huangniuniu.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class HuangniuniuCorsConfiguration {

    @Bean
    public CorsFilter corsFilter(){

        //1、初始化cor配置对象
        CorsConfiguration configuration=new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        configuration.addAllowedOrigin("http://manage.huangniuniu.com");
        configuration.addAllowedOrigin("http://www.huangniuniu.com");
        //2) 是否发送Cookie信息
        configuration.setAllowCredentials(true);
        //3) 允许的请求方式
        /*configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");*/
        configuration.addAllowedMethod("*");//代表所有请求方法
        // 4）允许的头信息
        configuration.addAllowedHeader("*");//允许携带任何头信息


        //初始化cors配置源对象，
        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        //2.添加映射路径，我们拦截一切请求
        configurationSource.registerCorsConfiguration("/**",configuration);

        //3、返回corsFilter实例
        return new CorsFilter(configurationSource);
    }
}
