package com.huangniuniu.city;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.huangniuniu.city.mapper")
public class HuangniuniuCityApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuangniuniuCityApplication.class);
    }
}
