import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis2 {
    @Test
    public void testConn2(){
        //创建redis连接 ip+port
        Jedis jedis=new Jedis("192.168.127.128",6379);
        //设置key和value
        jedis.set("j:name:1","j_zhaoyun1");
        //根据key获得值
        System.out.println(jedis.get("j:name:1"));

        //lpush
        jedis.lpush("j:list:1","1","2","3");

        System.out.println(jedis.llen("j:list:1"));
    }
}
