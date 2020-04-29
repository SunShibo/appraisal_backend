package com.wisewin.api.util.redisUtils;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 * Redis client的辅助工具类
 * 用于连接Redis服务器 创建不同的Redis Server对应的客户端对象
 * @author Shibo Sun
 */
public class RedissonHandler {

    private volatile RedissonClient redisson;

    private final String port = "6379";

    private final String ip = "47.95.113.129";//47.95.113.129//"r-2zec622a372f0b04.redis.rds.aliyuncs.com"; "wisewin-tech.com"

    private final String password = "Jianding1!";


    /**
     * redisson get the RBucket
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        RBucket<T> rBucket = redisson.getBucket(key);
        return rBucket.get();
    }

    /**
     * redisson set the value
     *
     * @param key
     * @param value
     * @param expire 单位秒
     */
    public void set(String key, Object value, Long expire) {
        RBucket<Object> rBucket = redisson.getBucket(key);
        if (expire == null)
            rBucket.set(value);
        else
            rBucket.set(value, expire, TimeUnit.SECONDS);
    }

    /**
     * delete
     * @param key
     */
    public void delete(String key) {
        RBucket<Object> rBucket = redisson.getBucket(key);
        rBucket.delete() ;
    }

    /**
     * 获取map, 返回的map是一个副本
     *
     * @param key
     * @param <V>
     * @return
     */
    public <V> Map<String, V> getMap(String key) {

        RMap<String, V> rMap = redisson.getMap(key);
        Map<String, V> resultMap = new HashMap<String, V>();

        for (String tmp : rMap.keySet()) {
            resultMap.put(tmp, rMap.get(tmp));
        }
        return resultMap;
    }

    /**
     * 存入map,该方法不能累加put值
     *
     * @param key
     * @param value
     * @param expire
     * @param <K>
     * @param <V>
     */
    public <K, V> void setMap(String key, Map<K, V> value, Long expire) {
        RMap<K, V> rMap = redisson.getMap(key);
        rMap.clear();
        rMap.putAll(value);
        if (expire != null)
            rMap.expire(expire, TimeUnit.SECONDS);
    }


    /**
     * 获取Set集合
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> Set<T> getSet(String key) {
        RSet<T> rSet = redisson.getSet(key);
        Set<T> set = new HashSet<T>();
        for (T t : rSet) {
            set.add(t);
        }
        return set;
    }

    /**
     * 保存Set集合
     *
     * @param key
     * @param value
     * @param expire
     * @param <T>
     */
    public <T> void setRSet(String key, Set<T> value, Long expire) {
        RSet<T> rSet = redisson.getSet(key);
        rSet.clear();
        rSet.addAll(value);
        if (expire != null)
            rSet.expire(expire, TimeUnit.SECONDS);
    }

    /**
     * 获取redis中的list,返回的是个副本
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key) {
        RList<T> rList = redisson.getList(key);
        List<T> list = new ArrayList<T>();
        list.addAll(rList);
        return list;
    }

    /**
     * set list
     *
     * @param key
     * @param value
     * @param expire 单位秒
     * @param <T>
     */
    public <T> void setList(String key, List<T> value, Long expire) {
        RList<T> rList = redisson.getList(key);
        rList.clear();
        rList.addAll(value);
        if (expire != null) {
            rList.expire(expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取队列
     *
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(String objectName) {
        RQueue<V> rQueue = redisson.getQueue(objectName);
        return rQueue;
    }

    /**
     * 获取双端队列
     *
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getRDeque(String objectName) {
        RDeque<V> rDeque = redisson.getDeque(objectName);
        return rDeque;
    }

    /**
     * 此方法不可用在Redisson 1.2 中
     * 在1.2.2版本中 可用
     * @param redisson
     * @param objectName
     * @return
     */
    /**
     public <V> RBlockingQueue<V> getRBlockingQueue(Redisson redisson,String objectName){
     RBlockingQueue rb=redisson.getBlockingQueue(objectName);
     return rb;
     }*/

    /**
     * 获取锁
     *
     * @param lockKey
     * @param expire 单位秒
     * @return
     */
    public RLock getRLock(String lockKey , Long expire) {
        RLock rLock = redisson.getLock(lockKey);
        if (expire != null ) {
            rLock.expire(expire , TimeUnit.SECONDS) ;
        }
        return rLock;
    }

    /**
     * 获取读写锁
     *
     * @param objectName
     * @return
     */
    public RReadWriteLock getReadWriteLock(String objectName) {
        return redisson.getReadWriteLock(objectName);
    }

    /**
     * 获取原子数
     *
     * @param objectName
     * @param expire 过期时间 单位秒
     * @return
     */
    public RAtomicLong getRAtomicLong(String objectName , Long expire) {
        RAtomicLong rAtomicLong = redisson.getAtomicLong(objectName);
        if (expire != null ) {
            rAtomicLong.expire(expire , TimeUnit.SECONDS) ;
        }
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(String objectName) {
        RCountDownLatch rCountDownLatch = redisson.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     *
     * @param objectName
     * @return
     */
    public <M> RTopic<M> getRTopic(String objectName) {
        RTopic<M> rTopic = redisson.getTopic(objectName);
        return rTopic;
    }

    /**
     * 使用config创建Redisson
     * Redisson是用于连接Redis Server的基础类
     *
     * @param config
     * @return
     */
    public RedissonClient getRedisson(Config config) {
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println("成功连接Redis Server");
        return redissonClient;
    }

    /**
     * 提供单例模式
     *
     * @return
     */
    public static final RedissonHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 关闭Redisson客户端连接
     */
    public void closeRedisson() {
        redisson.shutdown();
        System.out.println("成功关闭Redis Client连接");
    }


    /**
     * 通过内部类形式的加载保证懒汉式线程安全
     */
    private static class SingletonHolder {
        private static final RedissonHandler INSTANCE = new RedissonHandler();
    }

    /**
     * 私有构造
     */
    private RedissonHandler() {
        Config config = new Config();
        config.useSingleServer().setAddress(this.ip + ":" + this.port);
        config.useSingleServer().setPassword(this.password);
        this.redisson = this.getRedisson(config);
    }

    public static void main(String[] args) {
//        UserDO userDO = new UserDO();
//        userDO.setUsername("sunshibo");
//        RedissonHandler.getInstance().set("user", userDO, 100l);

//        RedissonHandler.getInstance().delete("user");
        Object u2 = RedissonHandler.getInstance().get("log");
        System.out.println((Boolean) u2);
    }
}
