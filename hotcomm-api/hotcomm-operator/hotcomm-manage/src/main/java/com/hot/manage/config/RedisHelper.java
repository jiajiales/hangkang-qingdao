package com.hot.manage.config;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import com.hot.manage.utils.JSONUtil;

@Component
public class RedisHelper {

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	public void set(final String key, final String value, final long liveTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					connection.set(key.getBytes("UTF-8"), value.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (liveTime > 0) {
					connection.expire(key.getBytes(), liveTime);
				}
				return 1L;
			}
		});
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

	public <T> void setList(String key, List<T> list, long liveTime) {
		String json = JSONUtil.toJson(list);
		set(key, json, liveTime);
	}

	public <T> List<T> getList(String key, Class<T> clz) {
		String value = get(key);
		if (value != null) {
			List<T> list = JSONUtil.forList(value, clz);
			return list;
		}
		return null;
	}

	public void del(final String key) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Long del = connection.del(key.getBytes());
				return del;
			}
		});
	}

	public boolean expire(String key, long liveTime) {
		return redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
	}
}
