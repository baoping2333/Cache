package com.lg.guava.demo;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.WritePolicy;

public class As1 {
    public static void main(String[] args) {
        //IP+port
        AerospikeClient client=new AerospikeClient("192.168.127.128",3000);

        //写策略
        WritePolicy wp=new WritePolicy();
        //超时时间
        wp.setTimeout(1000);

        /*
            key
         */
        Key k1=new Key("test","user1",1);
        /*
        bins
         */
        // KV
        Bin b11=new Bin("name","zhangfei");
        Bin b12=new Bin("sex","M");
        Bin b13=new Bin("age",23);

        //写值
        client.put(wp,k1,b11,b12,b13);
        //读值
        Record r1=client.get(wp,k1,"name","age","sex");

        System.out.println(r1);

        System.out.println("===================================");

        Key k2=new Key("test","user1",2);
        /*
        bins
         */
        // KV
        Bin b21=new Bin("name","diaochan");
        Bin b22=new Bin("sex","F");
        Bin b23=new Bin("age",21);

        //写值
        client.put(wp,k2,b21,b22,b23);

        /*
        取得指定key的数据
         */
        //批量执行策略
        BatchPolicy bp=new BatchPolicy(wp);
        //key的数组
        Key[] ks={k1,k2};

        //循环输出
        for(Record r:client.get(bp,ks)){
            System.out.println(r);
        }



    }
}
