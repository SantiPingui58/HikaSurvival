package me.santipingui58.survival.io.redis;

import java.util.Set;

import me.santipingui58.survival.HikaSurvival;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisManager {

	
	HikaSurvival hikaSurvival;
	String password;
	JedisPool pool;
	public RedisManager(HikaSurvival hikaSurvival) {
		String host = hikaSurvival.getConfig().getString("redis.host");
		int port = hikaSurvival.getConfig().getInt("redis.port");
		 password = hikaSurvival.getConfig().getString("redis.password");
		 pool = new JedisPool(host, port);
		this.hikaSurvival = hikaSurvival;
	}
	
	
	public String get(String key) {
		try (Jedis j = pool.getResource()) {
			  j.auth(password);
		   return j.get(key);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void set(String key, String value) {
		if (value==null) return;
		try (Jedis j = pool.getResource()) {
			 j.auth(password);
		   j.set(key, value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addToSet(String key,String value) {
		Jedis j = null;
		try {
		   j = pool.getResource();
		   j.auth(password);
		   j.sadd(key, value);
		} finally {
		   j.close();
		}
	}
	
	
	
	public Set<String> getSet(String key) {
		Jedis j = null;
		try {
		   j = pool.getResource();
		 j.auth(password);
		 return  j.smembers(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
		   j.close();
		}
	}
	
	
	

	
	
	public void removeFromSet(String key,String value) {
		Jedis j = null;
		try {
		   j = pool.getResource();
		  j.auth(password);
		   j.srem(key, value);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			j.close();
		}
		
	}
	/*
	
	public List<String> getList(String key) {
		try (Jedis j = Pool.getResource()) {
			  if (Main.password!=null)   j.auth(Main.password);
		 return  j.lrange(key, 0, -1);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void addToList(String key,String s) {
		Jedis j = null;
		try {
		   j = Pool.getResource();
		   if (Main.password!=null)  j.auth(Main.password);
		   if (getList(key).contains(s)) return;
		   j.lpush(key, s);
		} finally {
		   j.close();
		}
	}
	
	public void removeFromList(String key,String s) {
		Jedis j = null;
		try {
		   j = Pool.getResource();
		   if (Main.password!=null)  j.auth(Main.password);
		   j.lpush(key, s);
		   j.lrem(key, 0, s);
		} finally {
		   j.close();
		}
	}
	
	public void delete(String key) {
		Jedis j = null;
		try {
		   j = Pool.getResource();
		   if (Main.password!=null)  j.auth(Main.password);
		   j.del(key);
		} finally {
		   j.close();
		}
	}
	
	public void setList(String key, List<String> list) {
		try (Jedis j = Pool.getResource()) {
			  if (Main.password!=null)    j.auth(Main.password);
		   for (String s : list) {
			   j.lpush(key, s);
		   }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
