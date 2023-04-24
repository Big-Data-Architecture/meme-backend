package com.bda.userservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClearCacheTask {

    @Autowired
    CacheManager cacheManager;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void evictAllCaches(){
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}
