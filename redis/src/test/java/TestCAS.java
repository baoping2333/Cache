import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCAS {

    public static void main(String[] args){
        String redisKey = "lock";

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            Jedis jedis = new Jedis("192.168.127.128", 6380);
            // 初始值
            jedis.set(redisKey, "0");
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 1000; i++) {

            executorService.execute(() -> {

                Jedis jedis1 = new Jedis("192.168.127.128", 6380);
                try {
                    jedis1.watch(redisKey); //第二次 前面改过的值
                    String redisValue = jedis1.get(redisKey);
                    int valInteger = Integer.valueOf(redisValue);
                    String userInfo = UUID.randomUUID().toString();

                    // 没有秒完
                    if (valInteger < 20) {
                        Transaction tx = jedis1.multi();
                        //自增
                        tx.incr(redisKey);
                        // watch
                        List list = tx.exec();
                        // 秒成功   失败返回空list而不是空
                        if (list != null && list.size() > 0) {

                            System.out.println("用户：" + userInfo + "，秒杀成功！当前成功人数：" + (valInteger + 1));

                        }
                        // 版本变化，被别人抢了。
                        else {
                            System.out.println("用户：" + userInfo + "，秒杀失败");
                        }
                    }
                    // 秒完了
                    else {
                        System.out.println("已经有20人秒杀成功，秒杀结束");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis1.close();
                }

            });
        }
        executorService.shutdown();


    }
}
