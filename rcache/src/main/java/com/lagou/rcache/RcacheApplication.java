package com.lagou.rcache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.lagou.rcache.dao")
@EnableCaching
public class RcacheApplication {

    public static void main(String[] args) {

        SpringApplication.run(RcacheApplication.class, args);
    }

}
