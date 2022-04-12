import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis {
    @Test
    public void testConn(){
        Jedis redis = new Jedis("192.168.127.128", 6379);
        redis.set("jedis:name:1","jd-zhangfei");
        System.out.println(redis.get("jedis:name:1"));
        redis.lpush("jedis:list:1","1","2","3","4","5");
        System.out.println(redis.llen("jedis:list:1"));
    }
}
