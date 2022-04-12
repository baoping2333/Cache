package com.lg.guava.demo;

import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.WritePolicy;

import java.beans.BeanInfo;

public class As2 {
    public static void main(String[] args) {

        Host[] hs=new Host[]{
                new Host("192.168.127.128",3000),
                new Host("192.168.127.131",3000)
        };
        ClientPolicy cp=new ClientPolicy();

        //连接集群
        AerospikeClient client=new AerospikeClient(cp,hs);


        WritePolicy wp=new WritePolicy();
        wp.setTimeout(1000);

        Key k1=new Key("test","user2",1);

        Bin b1=new Bin("name","zhangfei-c");
        Bin b2=new Bin("age",23);
        //写值
        client.put(wp,k1,b1,b2);

        Record r1=client.get(wp,k1,"name","age");

        System.out.println(r1);

    }
}
