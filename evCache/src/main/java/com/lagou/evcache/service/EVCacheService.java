package com.lagou.evcache.service;

import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EVCacheService {

    @Autowired
    private EVCache evCache;

    public void setKey( String key, String value, int timeToLive ) {
        try {
            evCache.set( key, value, timeToLive );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getKey( String key ) {
        String value = null;
        try {
            value = evCache.get(key);
        } catch (EVCacheException e) {
            e.printStackTrace();
        }
        return value;
    }
}
