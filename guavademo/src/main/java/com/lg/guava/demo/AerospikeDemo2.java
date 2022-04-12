package com.lg.guava.demo;

import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.WritePolicy;

public class AerospikeDemo2 {
    public static void main(String[] args) {
        Host[] hosts = new Host[]{
                new Host("192.168.127.128", 3000),
                new Host("192.168.127.131", 3000)
        };
        ClientPolicy policy = new ClientPolicy();

        AerospikeClient client = new AerospikeClient(policy, hosts);


        //写策略
        WritePolicy wp = new WritePolicy();
        //超时时间
        wp.setTimeout(500);


        Key key1 = new Key("test", "SUser", "11");
        Bin bin11 = new Bin("name", "zhangfei-c");
        Bin bin12 = new Bin("age", 25);
        Bin bin13 = new Bin("sex", "M-c");
        client.put(wp, key1, bin11, bin12, bin13);

        Key key2 = new Key("test", "SUser", "22");
        Bin bin21 = new Bin("name", "zhaoyun-c");
        Bin bin22 = new Bin("age", 24);
        Bin bin23 = new Bin("sex", "M-c");
        client.put(wp, key2, bin21, bin22, bin23);


        Record r1 = client.get(wp, key1, "name", "age", "sex");
        System.out.println(r1);


    }
}
