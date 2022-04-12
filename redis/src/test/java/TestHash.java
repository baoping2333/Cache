import com.lagou.redis.utils.Crc32;
import org.junit.Test;

import java.util.UUID;

public class TestHash {
    @Test
    public void testCrc32(){
        System.out.println(Crc32.calculate("u001".getBytes()));
        System.out.println("u001".hashCode());
        System.out.println(UUID.randomUUID().toString());
    }
}
