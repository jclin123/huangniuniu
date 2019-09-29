package com.huangniuniu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.huangniuniu.oder.mapper")
public class HuangniuniuOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuangniuniuOrderApplication.class);
    }
}
