package com.run.crawler.weibo.entity.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.util.cache.VariableCache;

public class VelocityBeanPool {

	static PoolableObjectFactory factory = new VelocityBeanFactory();
	static GenericObjectPool pool = new GenericObjectPool(factory, VariableCache.VelocityBeanPool.maxActive,
			GenericObjectPool.WHEN_EXHAUSTED_GROW, VariableCache.VelocityBeanPool.maxWait, true, true);

	public static VelocityBean borrowObject() {
		VelocityBean velocityBean = null;
		try {
			return (VelocityBean) pool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return velocityBean;
	}

	public static void returnObject(Object object) {
		try {
			pool.returnObject(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
