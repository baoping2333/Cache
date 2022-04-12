package com.lagou.redis.distribute;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool pool = null;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10 * 1000);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "192.168.127.128", 6380, 10 * 1000);
    }

    /**
     * 获得Jedis实例
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
