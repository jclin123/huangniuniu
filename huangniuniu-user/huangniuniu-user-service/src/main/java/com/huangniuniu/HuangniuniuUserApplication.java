package com.huangniuniu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.huangniuniu.user.mapper")
public class HuangniuniuUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(HuangniuniuUserApplication.class);
    }
}
