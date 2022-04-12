import com.lagou.redis.distribute.RedisDistributedLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRDL {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
        //设置每500ms请求自旋一次，超时时间为5s,锁过期时间为5s
            RedisDistributedLock lock1 = new RedisDistributedLock("lock", 500l, 10 * 1000l, 5 * 1000l);
            lock1.lock();
            try {
                //占用锁４ｓ钟
                Thread.sleep(4 * 1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock1.unlock();
        });

        RedisDistributedLock lock2 = new RedisDistributedLock("lock", 500l, 10 * 1000l, 5 * 1000l);
        lock2.lock();
        Thread.sleep(2000l);
        lock2.unlock();


    }
}
