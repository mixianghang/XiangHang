package com.run.crawler.weibo.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.cache.VariableCache;

public class VecolityWriter implements VecolityWrite {

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * velocity固化velocityBean到磁盘
	 */
	public int write(VelocityBean velocityBean) {

		afterWrite(velocityBean);
		return 0;
	}

	public int write(List<VelocityBean> velocityBeanList) {
		if (velocityBeanList.size() == 0)
			return 0;
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(
					VariableCache.vecolityWriter.propertiesFile);
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e2) {
			log.error(e2.getMessage());
		} catch (IOException e2) {
			log.error(e2.getMessage());
		}
		try {
			Velocity.init(properties);
		} catch (Exception e1) {
			log.error(e1.getMessage());
		}
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("velocityBeanList", velocityBeanList);
		String fileName = this.job.toString() + "_"
				+ System.currentTimeMillis() + ".run";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(folder + fileName.toLowerCase());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(fos, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		try {
			Velocity.mergeTemplate("trsMetadataStoreConfig.vm", "UTF-8",
					velocityContext, writer);
		} catch (Exception e) {
			log.error("VecolityWriter write file failed: " + fileName + " "
					+ e.getMessage());
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException ex) {
				log.error("VecolityWriter write file failed: " + fileName + " "
						+ ex.getMessage());
			}
		}

		for (VelocityBean velocityBean : velocityBeanList) {
			afterWrite(velocityBean);
		}
		return 0;
	}

	/**
	 * 写完，将velocityBean归还给对象池
	 * 
	 * @param velocityBean
	 */
	private void afterWrite(VelocityBean velocityBean) {
		VelocityBeanPool.returnObject(velocityBean);
	}

	String folder = VariableCache.vecolityWriter.folder;

	Job job;

	public VecolityWriter(Job job) {
		this.job = job;
	}

}
