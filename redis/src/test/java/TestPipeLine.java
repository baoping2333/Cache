import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class TestPipeLine {
    @Test
    public void testPipeline(){
        Jedis redis = new Jedis("192.168.127.128", 6379);

        Pipeline pipe = redis.pipelined();
        for (int i = 0; i <50000; i++) {
            pipe.set("key_"+String.valueOf(i),String.valueOf(i));
        }
        //将封装后的PIPE一次性发给redis
        pipe.sync();
    }
}
