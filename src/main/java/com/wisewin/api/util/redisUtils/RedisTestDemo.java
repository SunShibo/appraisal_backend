package com.wisewin.api.util.redisUtils;


import org.redisson.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shibo on 16/11/18.
 */
public class RedisTestDemo {
    public static void main(String[] args) throws InterruptedException {
        new RedisTestDemo().testGetSaveSet();
    }

    /**
     * RBucket 映射为 redis server 的 string 类型
     * 只能存放最后存储的一个字符串
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type objTest
     * 查看key的值 ---->get objTest
     */
    public void testGetSaveObj() {
        System.out.println(">>>>>>>>>测试 testGetSaveObj.");
        // 存储
        String value = "this is a test value";
        RedissonHandler.getInstance().set("objTest", value, 100l);

        // 输出
        System.out.println("print the result: " + RedissonHandler.getInstance().get("objTest"));
    }

    /**
     * RMap  映射为  redis server 的 hash 类型
     * 分为
     * put(返回键值) 、 fast(返回状态)
     * 同步    异步
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type mapTest
     * 查看key的值 ---->hgetall mapTest
     *
     */
    public void testGetSaveMap() {
        System.out.println(">>>>>>>>>测试 testGetSaveMap.");
        Map<String , String > map = new HashMap<String, String>() ;
        map.put("key1" , "value1") ;
        map.put("key2" , "value2") ;

        RedissonHandler.getInstance().setMap("mapTest" , map , 100l);

        Map<String, Object> mapTest = RedissonHandler.getInstance().getMap("mapTest");
        for (String key : mapTest.keySet()) {
            System.out.println(key + ": " + mapTest.get(key));
        }
    }

    /**
     * RList 映射为 redis server的list类型
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type listTest
     * 查看key的值 ---->lrange listTest 0 10
     */
    public void testGetSaveList() {
        System.out.println(">>>>>>>>>测试 testGetSaveList.");
        List<String> list = new ArrayList<String>() ;
        list.add("111") ;
        list.add("222") ;

        RedissonHandler.getInstance().setList("listTest" , list , 100l);

        List<String> listTest = RedissonHandler.getInstance().getList("listTest");
        for (String s : listTest) {
            System.out.println(s);
        }
    }

    /**
     * RSet 映射为 redis server 的set 类型
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type setTest
     * 查看key的值 ---->smembers setTest
     */
    public void testGetSaveSet() {
        System.out.println(">>>>>>>>>测试 testGetSaveSet.");
        Set<String> set = new HashSet<String>();
        set.add("1111") ;
        set.add("2222") ;
        RedissonHandler.getInstance().setRSet("setTest" , set , 100l);
        Set<String> setTest = RedissonHandler.getInstance().getSet("setTest");
        System.out.println(setTest.size() + ":Size");
        for (String s : setTest) {
            System.out.println(s);
        }
    }

    /**

          * RDeque 映射为 redis server 的 list类型
          * 双端队列--对头和队尾都可添加或者移除，也遵循队列的先入先出
          * redis server 命令:
          * 查看所有键---->keys *
          * 查看key的类型--->type testDeque
          * 查看key的值 ---->lrange testDeque 0 10
          */
    public void testGetRDeque() {
        RDeque<Integer> rDeque = RedissonHandler.getInstance().getRDeque("testDeque");
        //清空双端队列
        rDeque.clear();
        Collection<Integer> c = Arrays.asList(12, 45, 12, 34, 56, 78);
        rDeque.addAll(c);
        //对头添加元素
        rDeque.addFirst(100);
        //队尾添加元素
        rDeque.addLast(200);
        System.out.println(Arrays.toString(rDeque.toArray()));
        //查看对头元素
        System.out.println(rDeque.peek());
        System.out.println(rDeque.peekFirst());
        //查看队尾元素
        System.out.println(rDeque.peekLast());
        System.out.println(Arrays.toString(rDeque.toArray()));
        //移除对头元素
        System.out.println(rDeque.poll());
        System.out.println(rDeque.pollFirst());
        //移除队尾元素
        System.out.println(rDeque.pollLast());
        System.out.println(Arrays.toString(rDeque.toArray()));
        //添加队尾元素
        System.out.println(rDeque.offer(300));
        System.out.println(rDeque.offerFirst(400));
        System.out.println(Arrays.toString(rDeque.toArray()));
        //移除对头元素
        System.out.println(rDeque.pop());
        //显示双端队列的元素
        System.out.println(Arrays.toString(rDeque.toArray()));
    }

    /**
     * RLock 映射为redis server的string 类型
     * string中存放 线程标示、线程计数
     * 查看所有键---->keys *
     * 查看key的类型--->type lockTest
     * 查看key的值 ---->hgetall lockTest
     * 如果想在redis server中 看到 lockTest
     * 就不能使用   rLock.unlock();
     * 因为使用 rLock.unlock(); 之后 就会删除redis server中的 lockTest
     */
    public void testGetRLock() throws InterruptedException {
        RLock rLock = RedissonHandler.getInstance().getRLock("lockTest" , 100l);

        rLock.lock();
        //
        System.out.println(rLock.getName());
        System.out.println(rLock.getHoldCount());
        System.out.println(rLock.isLocked());

        Thread.sleep(10000); // 睡十秒以便观察状态

        rLock.unlock();
        System.out.println("finish");
    }

    /**
     * RAtomicLong 映射为redis server的string 类型
     * string中数值
     * 查看所有键---->keys *
     * 查看key的类型--->type testAtomicLong
     * 查看key的值 ---->get testAtomicLong
     */
    public void testGetRAtomicLong() {
        RAtomicLong rAtomicLong = RedissonHandler.getInstance().getRAtomicLong("testAtomicLong" , 100l);
        rAtomicLong.set(100);
        System.out.println(rAtomicLong.addAndGet(200));
        System.out.println(rAtomicLong.decrementAndGet());
        System.out.println(rAtomicLong.get());
    }

        /**
     * RCountDownLatch 映射为redis server的string 类型
     * string中数值
     * 闭锁--等待其他线程中的操作都做完 在进行操作
     * 查看所有键---->keys *
     * 查看key的类型--->type testCountDownLatch
     * 查看key的值 ---->get testCountDownLatch
     */
    public void testGetRCountDownLatch() throws InterruptedException {
        RCountDownLatch rCountDownLatch = RedissonHandler.getInstance().getRCountDownLatch("testCountDownLatch");
        System.out.println(rCountDownLatch.getCount());
        //rCountDownLatch.trySetCount(1l);
        System.out.println(rCountDownLatch.getCount());
        rCountDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println(rCountDownLatch.getCount());
    }

    /**
     * 消息队列的订阅者
     * @throws InterruptedException
     */
//    public void testGetRTopicSub() throws InterruptedException {
//        RTopic<String> rTopic=RedissonHandler.getInstance().getRTopic("testTopic");
//        rTopic.addListener(new MessageListener<String>() {
//
//            @Override
//            public void onMessage(String msg) {
//                // TODO Auto-generated method stub
//                System.out.println("你发布的是:"+msg);
//            }
//        });
//        //等待发布者发布消息
//        RCountDownLatch rCountDownLatch=RedissonHandler.getInstance().getRCountDownLatch( "testCountDownLatch");
//        rCountDownLatch.trySetCount(1);
//        rCountDownLatch.await();
//    }

    /**
     * 消息队列的发布者
     */
    public void testGetRTopicPub() {
        RTopic<String> rTopic = RedissonHandler.getInstance().getRTopic("testTopic");
        System.out.println(rTopic.publish("今天是儿童节，大家儿童节快乐"));
        //发送完消息后 让订阅者不再等待
        RCountDownLatch rCountDownLatch = RedissonHandler.getInstance().getRCountDownLatch("testCountDownLatch");
        rCountDownLatch.countDown();
    }

    public void testLock() {
        String key = "";
        long expair = 1l;
        RedissonHandler.getInstance().getRLock(key , expair);
    }
}
