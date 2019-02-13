package com.hotcomm.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisHelper {

	@Resource
	private RedisTemplate<String, ?> redisTemplate;
	
	private final long DEFAULTTIME = 60*30;
	
	
	public RedisHelper(int dATABASE_INDEX) {
		super();
	}

	public RedisHelper selectIndex(int databseIndex) {
		return this;
	}
	
	public void set(final String key, final String value) {
		set(key, value, DEFAULTTIME);
	
	}
	
	public Set<String> keys(final String pattern){
		Set<String> result = redisTemplate.execute(new RedisCallback<Set<String>>() {

			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte []> keys = connection.keys(pattern.getBytes());
				Set<String> result = new HashSet<>();
				if(keys!=null&&keys.size()>0) {
					for(byte [] b:keys) {
						result.add(new String(b));
					}
				}
				return result;
			}
			
		});
		return result;
	}
	
	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] value = connection.get(serializer.serialize(key));
				return serializer.deserialize(value);
			}
		});
		return result;
	}

	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}
	
    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final String key,final String value,final long liveTime){
        try {
			this.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"), liveTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
	
    
    public void del(final String key) {
    	this.redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(key.getBytes());
                return 1L;
            }
        });
    }
    
    public long getKeyExpire(String key) {
    	long expire = 0l;
    	expire = this.redisTemplate.getExpire(key);
    	return expire;
    }
    
    public Boolean existsKey(String key) {
    	return existsKey(key.getBytes());
    }
    
    public Boolean existsKey(final byte [] key) {
    	return this.redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key);
            }
        });
    }
    
}
