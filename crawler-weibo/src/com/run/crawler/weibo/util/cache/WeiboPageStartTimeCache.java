package com.run.crawler.weibo.util.cache;

import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class WeiboPageStartTimeCache {

	public int getStartTime(Task task, Job job) {
		int startTime = 0;
		if (task == Task.Sina) {
			switch (job) {
			case Weibo:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaWeiboStartTime;
				break;
			case Friend:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaFriendStartTime;
				break;
			case Follower:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaFollowerStartTime;
				break;
			case Search:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaSearchStartTime;
				break;
			case UserShow:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaUserShowStartTime;
				break;
			case Trend:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaTrendStartTime;
				break;
			case WeiboFromUserTimeLine:
				startTime = VariableCache.GenWeiboPageFromAccount.sinaWeiboStartTime;
				break;
			}
		} else if (task == Task.Tencent) {
			switch (job) {
			case Weibo:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentWeiboStartTime;
				break;
			case Friend:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentFriendStartTime;
				break;
			case Follower:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentFollowerStartTime;
				break;
			case Search:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentSearchStartTime;
				break;
			case UserShow:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentUserShowStartTime;
				break;
			case Trend:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentTrendStartTime;
				break;
			case WeiboFromUserTimeLine:
				startTime = VariableCache.GenWeiboPageFromAccount.tencentWeiboFromUserTimeLineStartTime;
				break;
			}
		} else if (task == Task.Twitter) {
			switch (job) {
			case Weibo:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterWeiboStartTime;
				break;
			case Friend:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterFriendStartTime;
				break;
			case Follower:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterFollowerStartTime;
				break;
			case Search:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterSearchStartTime;
				break;
			case UserShow:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterUserShowStartTime;
				break;
			case Trend:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterTrendStartTime;
				break;
			case WeiboFromUserTimeLine:
				startTime = VariableCache.GenWeiboPageFromAccount.twitterWeiboStartTime;
				break;
			}
		} else if (task == Task.Sohu) {
			switch (job) {
			case Weibo:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuWeiboStartTime;
				break;
			case Friend:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuFriendStartTime;
				break;
			case Follower:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuFollowerStartTime;
				break;
			case Search:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuSearchStartTime;
				break;
			case UserShow:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuUserShowStartTime;
				break;
			case Trend:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuTrendStartTime;
				break;
			case WeiboFromUserTimeLine:
				startTime = VariableCache.GenWeiboPageFromAccount.sohuWeiboStartTime;
				break;
			}
		} else if (task == Task.Netease) {
			switch (job) {
			case Weibo:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseWeiboStartTime;
				break;
			case Friend:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseFriendStartTime;
				break;
			case Follower:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseFollowerStartTime;
				break;
			case Search:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseSearchStartTime;
				break;
			case UserShow:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseUserShowStartTime;
				break;
			case Trend:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseTrendStartTime;
				break;
			case WeiboFromUserTimeLine:
				startTime = VariableCache.GenWeiboPageFromAccount.neteaseWeiboStartTime;
				break;
			}
		}
		return startTime;
	}
}
