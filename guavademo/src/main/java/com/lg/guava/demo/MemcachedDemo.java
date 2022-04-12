package com.lg.guava.demo;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

public class MemcachedDemo {
    public static void main(String[] args) {

        try{
            // 连接本地的 Memcached 服务
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("192.168.127.128", 11211));
            System.out.println("Connection to server sucessful.");

            // 存储数据
            Future fo = mcc.set("runoob", 900, "Free Education");

            // 查看存储状态
            System.out.println("set status:" + fo.get());

            // 输出值
            System.out.println("runoob value in cache - " + mcc.get("runoob"));

            // 关闭连接
            mcc.shutdown();

        }catch(Exception ex){
            System.out.println( ex.getMessage() );
        }
    }
}
