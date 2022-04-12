package com.lg.guava.demo;

import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;

public class EVCacheDemo {
    public static void main(String[] args) throws Exception {
//        System.setProperty("EVCACHE_SERVER", "SERVERGROUP=192.168.127.128:11211");
//        EVCache evCache = new EVCache.Builder().setAppName("EVCACHE_SERVER").build();

        String deploymentDescriptor = System.getenv("EVCACHE_SERVER");
        if ( deploymentDescriptor == null ) {
            deploymentDescriptor = "SERVERGROUP1=192.168.127.131:11211";
        }
        System.setProperty("EVCACHE_1.use.simple.node.list.provider", "true");
        System.setProperty("EVCACHE_1-NODES", deploymentDescriptor);
        EVCache evCache = new EVCache.Builder().setAppName("EVCACHE_1").build();
        // s:key  t :value i:ttl 秒
        evCache.set("name","zhangfei",10);
        String v = evCache.get("name");
        System.out.println(v);
    }
}
