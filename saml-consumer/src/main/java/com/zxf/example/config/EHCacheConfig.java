package com.zxf.example.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EHCacheConfig {
    @Bean
    public Cache consumeCache() {
        CacheManager cacheManager = CacheManager.create(EHCacheConfig.class.getClassLoader().getResource("ehcache.xml"));
        return cacheManager.getCache("ConsumeCache");
    }
}
