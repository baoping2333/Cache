package com.lg.guava.demo;

import com.google.common.cache.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaDemo {
    public  static  void main(String args[]) throws Exception {
        LoadingCache<String,Object> cache = CacheBuilder.newBuilder()
                // 最大3个       //同时支持CPU核数线程写缓存
                .maximumSize(3).concurrencyLevel(Runtime.getRuntime().availableProcessors()).refreshAfterWrite(3,TimeUnit.SECONDS)
                .recordStats().removalListener(new RemovalListener<Object, Object>() {
                    public void onRemoval(RemovalNotification<Object, Object> notification){
                        System.out.println(notification.getKey()+":"+notification.getCause());
                    }
                })
                .build(
                        new CacheLoader<String, Object>() {
                            @Override
                            public String load(String s) throws Exception {

                                return Constants.hm.get(s);
                            }
                        }
                );
        /*
            初始化cache
         */
        initCache(cache);
        System.out.println(cache.size());
        displayCache(cache);
        System.out.println("=============================");
        Object value = new Object();
        cache.put("1",value);

        value = new Object();//原对象不再有强引用
        System.gc();
        System.out.println(cache.getIfPresent("1"));


    }

    public static Object get(String key,LoadingCache cache)throws Exception{
        Object value=cache.get(key, new Callable() {
            @Override
            public Object call() throws Exception {
                Object v= Constants.hm.get(key);
                //设置回缓存
                cache.put(key,v);
                return v;
            }
        });
        return value;
    }

    public static void initCache(LoadingCache cache)throws Exception{
        /*
            前三条记录
         */
        for(int i=1;i<=3;i++){
            cache.get(String.valueOf(i));
        }
    }

    /**
     * 获得当前缓存的记录
     * @param cache
     * @throws Exception
     */
    public static void displayCache(LoadingCache cache)throws Exception{

        Iterator its=cache.asMap().entrySet().iterator();
        while(its.hasNext()){
            System.out.println(its.next().toString());
        }



    }
}
