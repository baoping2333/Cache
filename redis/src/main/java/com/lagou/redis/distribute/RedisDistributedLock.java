package com.lagou.redis.distribute;

import redis.clients.jedis.Jedis;

public class RedisDistributedLock {

    //每次请求的时间为200ms
    private  Long PER_REQ_MILL = null;
    //总体等待时间不超过10s
    private  Long WAIT_TIME_OUT = null;
    //锁名称
    private  String LOCK_NAME = null;
    //锁过期时间
    private Long EXPIRE_TIME = null;

    public RedisDistributedLock() {
        LOCK_NAME = Thread.currentThread().getName();
        PER_REQ_MILL = 200l; //默认每次自旋请求间隔200ms
        WAIT_TIME_OUT = 10 * 1000l; //默认自旋时间10s
        EXPIRE_TIME = 2 * 1000l;//默认过期时间2s
    }

    public RedisDistributedLock(String lockName, Long perReqMill, Long waitTimeOut, Long expireTime) {
        LOCK_NAME = lockName;
        PER_REQ_MILL = perReqMill;
        WAIT_TIME_OUT = waitTimeOut;
        EXPIRE_TIME = expireTime;
    }


    public void lock() {
        //先用set key value nx ex expireAt 命令查询是否已经有了该锁
        Jedis jedis = JedisUtil.getJedis();
        String isSet = jedis.set(this.LOCK_NAME, this.LOCK_NAME, "NX", "EX", this.EXPIRE_TIME / 1000);
        if ("OK".equals(isSet)) {
            //没有该锁，则直接占用
            System.out.println("<<<<<线程" + Thread.currentThread().getName() + "占用" + this.LOCK_NAME + "锁成功");
            return;
        } else {
            //该锁仍然存在,原地自旋，每隔一段时间请求一次，直至成功或超时抛出错误
            spin();
        }
    }

    /**
     * 锁的自旋
     */
    private void spin() {
        //进入时的时间
        long beginTime = System.currentTimeMillis();
        //获取锁
        Jedis jedis = JedisUtil.getJedis();
        String isSet = jedis.set(this.LOCK_NAME, this.LOCK_NAME, "NX", "EX", this.EXPIRE_TIME / 1000);
        if ("OK".equals(isSet)) {
            //没有该锁，则直接占用
            System.out.println("<<<<<线程" + Thread.currentThread().getName() + "占用" + this.LOCK_NAME + "锁成功");
        } else {
            while (true) {
                try {
                    //睡眠短暂时间继续请求
                    Thread.sleep(this.PER_REQ_MILL);
                    System.out.println("线程"+Thread.currentThread().getName()+"自旋睡眠"+this.PER_REQ_MILL+"毫秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.currentTimeMillis() - beginTime >= this.WAIT_TIME_OUT) {
                    //已经超时
                    System.out.println("线程"+Thread.currentThread().getName()+"超过了" + this.WAIT_TIME_OUT + "毫秒都无法获取到名为"+this.LOCK_NAME+"的锁，超时退出");
                    throw new RuntimeException("获取锁失败");
                } else {
                    //没有超时，继续请求
                    isSet = jedis.set(this.LOCK_NAME, this.LOCK_NAME, "NX", "EX", this.EXPIRE_TIME / 1000);
                    if ("OK".equals(isSet)) {
                        //上锁成功
                        System.out.println("<<<<<线程" + Thread.currentThread().getName() + "占用" + this.LOCK_NAME + "锁成功");
                        break;
                    }
                }
            }
        }
    }


    public void unlock() {
        Jedis jedis = JedisUtil.getJedis();
        //先获取该锁
        String lockValue = jedis.get(this.LOCK_NAME);
        if (lockValue == null) {
            System.out.println(">>>>>" + Thread.currentThread().getName() + "解除了对" + this.LOCK_NAME + "的占用");
            return;
        } else if (this.LOCK_NAME.equals(lockValue)){
            Long del = jedis.del(this.LOCK_NAME);
            System.out.println(">>>>>"+Thread.currentThread().getName() + "解除了对" + this.LOCK_NAME + "的占用");
            return;
        }
        System.out.println("*****"+Thread.currentThread().getName() + "无法解除对" + this.LOCK_NAME + "的占用");
    }
}
