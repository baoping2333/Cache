package com.lg.guava.demo;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;

public class AerospikeDemo {
    public static void main(String[] args) {
        AerospikeClient client = new AerospikeClient("192.168.127.128", 3000);
        //写策略
        WritePolicy policy = new WritePolicy();
        //超时时间
        policy.setTimeout(500);

        Key key1 = new Key("test", "SUser", "1");
        Bin bin11 = new Bin("name", "zhangfei");
        Bin bin12 = new Bin("age", 25);
        Bin bin13 = new Bin("sex", "M");
        client.put(policy, key1, bin11, bin12,bin13);

        Key key2 = new Key("test", "SUser", "2");
        Bin bin21 = new Bin("name", "zhaoyun");
        Bin bin22 = new Bin("age", 24);
        Bin bin23 = new Bin("sex", "M");
        client.put(policy, key2, bin21, bin22,bin23);



        Record r1 = client.get(policy, key1, "name", "age","sex");
        System.out.println(r1);


    }
}
