package com.run.crawler.weibo.entity.pool;

import org.apache.commons.pool.PoolableObjectFactory;

import com.run.crawler.weibo.entity.VelocityBean;

public class VelocityBeanFactory implements PoolableObjectFactory {

	// @Override
	public void activateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	// //@Override
	public void destroyObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	// @Override
	public Object makeObject() throws Exception {
		// TODO Auto-generated method stub
		return new VelocityBean();
	}

	// @Override
	public void passivateObject(Object arg0) throws Exception {
		if (arg0 instanceof VelocityBean)
			((VelocityBean) arg0).clear();
	}

	// @Override
	public boolean validateObject(Object arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
