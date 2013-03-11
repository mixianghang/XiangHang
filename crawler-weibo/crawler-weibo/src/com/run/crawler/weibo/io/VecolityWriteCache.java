package com.run.crawler.weibo.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.filter.Filter;
import com.run.crawler.weibo.util.filter.FilterChain;

public class VecolityWriteCache extends TimerTask implements VecolityWrite {

	{
		long delay = VariableCache.vecolityWriteCache.delay;
		long period = VariableCache.vecolityWriteCache.period;

		Timer timer = new Timer("VecolityWriteCache");
		timer.schedule(this, delay, period);
	}

	private VecolityWriteCache() {
		super();
	};

	private volatile List<VelocityBean> weiboList = new ArrayList<VelocityBean>();
	private volatile List<VelocityBean> friendList = new ArrayList<VelocityBean>();
	private volatile List<VelocityBean> followerList = new ArrayList<VelocityBean>();
	private volatile List<VelocityBean> userShowList = new ArrayList<VelocityBean>();
	private volatile List<VelocityBean> searchList = new ArrayList<VelocityBean>();
	private volatile List<VelocityBean> trendList = new ArrayList<VelocityBean>();

	private final Filter weiboFilter = new FilterChain();
	private final Filter friendFilter = new FilterChain();
	private final Filter followerFilter = new FilterChain();
	private final Filter userShowFilter = new FilterChain();
	private final Filter searchFilter = new FilterChain();
	private final Filter trendFilter = new FilterChain();
	private static final Logger log = Logger.getLogger(VecolityWriteCache.class);

	public int write(VelocityBean velocityBean) {

		Filter filter = null;
		List<VelocityBean> list = null;

		switch (velocityBean.getJob()) {
		case Weibo:
		case WeiboFromUserTimeLine:
			filter = weiboFilter;
			list = weiboList;
			break;
		case Friend:
			filter = friendFilter;
			list = friendList;
			break;
		case Follower:
			filter = followerFilter;
			list = followerList;
			break;
		case Search:
			filter = searchFilter;
			list = searchList;
			break;
		case UserShow:
			filter = userShowFilter;
			list = userShowList;
			break;
		case Trend:
			filter = trendFilter;
			list = trendList;
			break;
		default:
			break;
		}

		if (filter.contains(velocityBean.getMd5()))
			return 0;
		synchronized(list) {
			list.add(velocityBean);
		}
		return 1;
	}

	public int write(List<VelocityBean> list) {
      int i = 0;
      for (VelocityBean velocityBean : list) {
        i += write(velocityBean);
      }
      return i;
	}

	void clear() {
		synchronized (weiboList) {
			weiboList.clear();
		}
		synchronized (friendList) {
			friendList.clear();
		}
		synchronized (followerList) {
			followerList.clear();
		}
		synchronized (userShowList) {
			userShowList.clear();
		}
		synchronized (searchList) {
			searchList.clear();
		}
		synchronized (trendList) {
			trendList.clear();
		}
	}

	@Override
	public void run() {
		try {
			synchronized (weiboList) {
				new VecolityWriter(Job.Weibo).write(weiboList);
			}
			synchronized (friendList) {
				new VecolityWriter(Job.Friend).write(friendList);
			}
			synchronized (followerList) {
				new VecolityWriter(Job.Follower).write(followerList);
			}
			synchronized (userShowList) {
				new VecolityWriter(Job.UserShow).write(userShowList);
			}
			synchronized (searchList) {
				new VecolityWriter(Job.Search).write(searchList);
			}
			synchronized (trendList) {
				new VecolityWriter(Job.Trend).write(trendList);
			}
			clear();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private static class Proxy {
		private static VecolityWriteCache vecolityWriteCache = new VecolityWriteCache();

	}

	public static VecolityWriteCache newInstance() {
		return Proxy.vecolityWriteCache;
	}

}
