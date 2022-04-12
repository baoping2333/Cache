package com.lagou.evcache.config;

import com.netflix.evcache.EVCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EVCacheConfig {

    @Bean
    public EVCache evcache() {
        System.setProperty("EVCACHE_SERVER", "SERVERGROUP=192.168.127.128:11211");
        EVCache evCache = new EVCache.Builder().setAppName("EVCACHE_SERVER").build();
        return evCache;
    }
}
