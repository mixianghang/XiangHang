package com.run.crawler.weibo.util.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.util.Task;

/**
 * redis account 缓存
 * @author jerry
 *
 */
public class AccountRedisCache{
	
	static Logger log = Logger.getLogger(AccountRedisCache.class);
	
	private static String host = VariableCache.AccountRedisCache.host;
	private static int port = VariableCache.AccountRedisCache.port;
	
	private static Jedis jedis = init();
	
	private static final String A = "accountId";
	private static final String B = "task";
	private static final String C = "nickName";
	public static void add (Account account){
		jedis.connect();
		long accountId = account.getAccountId();
		Task task = account.getTask();
		String nickName = account.getNickName();
		log.info("add : "+ accountId + ":" + nickName);
		Map<byte[],byte[]> map = new HashMap<byte[],byte[]>();
		map.put(A.getBytes(), Long.toString(accountId).getBytes());
		map.put(B.getBytes(), Integer.toString(task.getI()).getBytes());
		map.put(C.getBytes(), nickName.getBytes());
		jedis.hmset(Long.toString(accountId).getBytes(), map);
		jedis.disconnect();
	}
	
	public static void update(Account account){
		del(account);
		add(account);
	}
	
	public static void del(Account account){
		jedis.connect();
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    log.info("del : "+ accountId + ":" + nickName);
		jedis.del(Long.toString(accountId).getBytes());
		jedis.disconnect();
	}
	
	/**
	 * 判断是否存在该帐号
	 * @param account
	 * @return
	 */
	public static boolean exit(Account account){
		jedis.connect();
		if(jedis.exists(Long.toString(account.getAccountId()).getBytes())){
			jedis.disconnect();
			return true;
		}else{
			jedis.disconnect();
			return false;
		}
	}
	
	private static Jedis init(){
		
		
		log.info("host : "+host);
		log.info("port : "+port);
		JedisPool pool = null;
		try {
			JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
			config.setMaxActive(500);// 最大活动的对象个数
			config.setMaxIdle(1000 * 60);// 对象最大空闲时间
			config.setMaxWait(1000 * 10L);// 获取对象时最大等待时间
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, host,
			       port, 5000);
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal(e.getMessage());
		}
        return pool.getResource();
	}
	
	/**
	 * 加载缓存中所有记录
	 * @return
	 */
	public static List<Account> getAll(){
		jedis.connect();
		List<Account> list = new ArrayList<Account>();
		Set<String> keySet = jedis.keys("*");
		if(null == keySet || keySet.size() == 0){
			return list;
		}
		for(String key : keySet){
			Account account = new Account();
			String type = jedis.type(key);
			if(!"hash".equals(type.toLowerCase())){
				continue;
			}
			Map<byte[],byte[]> map = jedis.hgetAll(key.getBytes());
			account.setAccountId(Long.parseLong(new String(map.get(A.getBytes()))));
			account.setTask(Task.getTask(Integer.parseInt(new String(map.get(B.getBytes())))));
			account.setNickName(new String(map.get(C.getBytes())));
			list.add(account);
		}
		jedis.disconnect();
		return list;
	}
}
