package com.lg.guava.demo.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lg.guava.demo.Constants;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class Demo4 {
    /**
     * 初始化缓存三个
     */
    public static void initCache(LoadingCache<String, Object> cache) throws Exception {
        for (int i = 1; i <= 3; i++) {
            //连接数据源   如果缓存没有则读取数据源
            cache.get(String.valueOf(i));
        }

    }

    /**
     * 显示缓存里的数据
     *
     * @param cache
     */
    public static void display(LoadingCache<String, Object> cache) {
        //利用迭代器
        Iterator its = cache.asMap().entrySet().iterator();
        while (its.hasNext()) {
            System.out.println(its.next().toString());
        }
    }

    /**
     * 读取缓存数据 如果没有则回调源数据并(自动)写入缓存
     *
     * @param key
     * @param cache
     */
    public static void get(String key, LoadingCache<String, Object> cache) throws Exception {
        cache.get(key, new Callable<Object>() {

            @Override //回调方法用于读源并写入缓存
            public Object call() throws Exception {
                //读源
                Object value = Constants.hm.get(key);
                //写回缓存
                // cache.put(key,value);
                return value;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        //CacheLoader的方式创建
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                /*
                     加附加的功能
                 */
                //弱值的删除
                .maximumSize(3).weakValues()
                .build(new CacheLoader<String, Object>() {

                    //读取数据源
                    @Override
                    public Object load(String key) throws Exception {
                        return Constants.hm.get(key);
                    }
                });
        //初始化缓存
        initCache(cache);
        System.out.println(cache.size());
        //显示缓存数据
        display(cache);

        Object v = new Object();
        cache.put("1", v);

        v = new Object();
        System.gc();
        System.out.println("================================");
        display(cache);


    }
}
