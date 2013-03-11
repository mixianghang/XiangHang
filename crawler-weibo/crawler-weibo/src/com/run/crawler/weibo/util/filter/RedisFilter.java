package com.run.crawler.weibo.util.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import com.run.crawler.weibo.util.cache.VariableCache;

public class RedisFilter extends Filter {

	private ShardedJedisPool pool;
	
	public RedisFilter(){
		init();
	}
	
	@Override
	public boolean contains(String key) {
		ShardedJedis jedis = pool.getResource();
		if(jedis.exists(key.getBytes())){
			pool.returnResource(jedis);
			return true;
		}
		pool.returnResource(jedis);
		return false;
	}

	@Override
	public void add(String key) {
		
		log.debug("RedisFilter add: "+key);
		
		ShardedJedis jedis = pool.getResource();
		jedis.set(key.getBytes(), "".getBytes());
		pool.returnResource(jedis);
	}
	
	private void init(){
		Map<String,Integer> hostAndPort = VariableCache.JredisFilter.hostAndPort;
		
		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
		config.setMaxActive(500);// 最大活动的对象个数
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
		config.setMaxWait(1000 * 10L);// 获取对象时最大等待时间
		config.setTestOnBorrow(true);
		
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(hostAndPort.size());
		for(Map.Entry<String, Integer> entry : hostAndPort.entrySet()){
			JedisShardInfo info = new JedisShardInfo(entry.getKey(),entry.getValue());
			jdsInfoList.add(info);
		}

		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,
				Sharded.DEFAULT_KEY_TAG_PATTERN);
	}
}
