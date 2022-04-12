import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class TestJDS {
    @Test
    public void testJDS(){
        JedisPoolConfig config = new JedisPoolConfig();


        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7001));

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7002));

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7003));

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7004));

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7005));

        jedisClusterNode.add(new HostAndPort("192.168.127.128", 7006));

        JedisCluster jcd = new JedisCluster(jedisClusterNode, config);

        jcd.set("name:001-c","zhangfei-c");

        String value = jcd.get("name:001-c");
    }
}
