package com.run.crawler.weibo.net.listener;

import java.util.LinkedList;
import java.util.List;

public class NetListenerChain implements NetListener {
	private final List<NetListener> list = new LinkedList<NetListener>();

	public String work(String message) {
		for (NetListener netListener : list) {
			message = netListener.work(message);
		}
		return message;
	}

	public NetListenerChain add(NetListener netListener) {
		if (list.contains(netListener))
			return this;
		list.add(netListener);
		return this;
	}

	private NetListenerChain() {
	};

	private static class Proxy {
		private static NetListenerChain netListenerChain = new NetListenerChain();
		static {
			netListenerChain.add(new HttpDisabledListener());
			netListenerChain.add(new AlarmListener());
		}
	}

	public static NetListener newInstance() {
		return Proxy.netListenerChain;
	}
}
