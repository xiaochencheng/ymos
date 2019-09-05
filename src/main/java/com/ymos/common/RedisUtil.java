package com.ymos.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

public final class RedisUtil {

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private static volatile boolean flag = false;

    static Properties prop = new Properties();

    public static void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        try {
                prop.load(RedisUtil.class.getResourceAsStream("/redis.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        config.setMaxActive(Integer.parseInt(prop.getProperty("redis.pool.maxActive")));
        config.setMaxIdle(Integer.parseInt(prop.getProperty("redis.pool.maxIdle")));
        config.setMaxWait(Integer.parseInt(prop.getProperty("redis.pool.maxWait")));
        config.setTestOnBorrow(TEST_ON_BORROW);
        jedisPool = new JedisPool(config, prop.getProperty("redis.ip")
                , Integer.parseInt(prop.getProperty("redis.port")), Integer.parseInt(prop.getProperty("redis.timeout")));
    }



    /**
     * 获取Jedis实例
     * @return
     */
    public  static Jedis getJedis() {
        synchronized(RedisUtil.class){
            if(!flag){
                init();
                flag = true;
            }
        }
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
  public static void expired24(String key){
      Jedis jedis = getJedis();
      try{
          if(jedis != null){
              jedis.expire(key,60*60*24);
          }
      }finally {
          returnResource(jedis);
      }

  }
    public static void set(String key,String value){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                jedis.set(key, value);
            }
        }finally {
            returnResource(jedis);
        }
    }

    public static void hset(String key,String field,String value){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                jedis.hset(key,field, value);
            }
        }finally {
            returnResource(jedis);
        }
    }
    public static String hget(String key,String field){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                return jedis.hget(key,field);
            }
        }finally {
            returnResource(jedis);
        }
        return null;
    }
    public static String get(String key){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                return jedis.get(key);
            }
        }finally {
            returnResource(jedis);
        }
        return null;
    }
    public static long incr(String key){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                return jedis.incr(key);
            }
        }finally {
            returnResource(jedis);
        }
        return 0;
    }

    public static void set(byte[] key,byte[] value){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                jedis.set(key, value);
            }
        }finally {
            returnResource(jedis);
        }
    }
    public static byte[] get(byte[] key){
        Jedis jedis = getJedis();
        byte[] value = null;
        try{
            if(jedis != null){
                value = jedis.get(key);
            }
        }finally {
            returnResource(jedis);
        }
        return value;
    }

    public static void del(byte[] key){
        Jedis jedis = getJedis();
        try{
            if(jedis != null){
                jedis.del(key);
            }
        }finally {
            returnResource(jedis);
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
