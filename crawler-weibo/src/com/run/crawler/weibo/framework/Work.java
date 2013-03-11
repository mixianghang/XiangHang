package com.run.crawler.weibo.framework;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.processor.chain.ProcessorChain;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseFollowerExtractor;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseFriendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseSearchExtractor;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseTrendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseUserShowExtractor;
import com.run.crawler.weibo.processor.extractor.impl.NeteaseWeiboFromUserTimeLineExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SinaFollowerExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SinaFriendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SinaWeiboExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SinaWeiboFromUserTimeLineExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SohuFollowerExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SohuFriendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SohuUserShowExtractor;
import com.run.crawler.weibo.processor.extractor.impl.SohuWeiboFromUserTimeLineExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TencentFollowerExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TencentFriendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TencentWeiboExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TencentWeiboFromUserTimeLineExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterFollowerExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterFriendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterSearchExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterTrendExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterUserShowExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterWeiboExtractor;
import com.run.crawler.weibo.processor.extractor.impl.TwitterWeiboFromUserTimelineExtractor;
import com.run.crawler.weibo.processor.fetcher.Fetcher;
import com.run.crawler.weibo.processor.post.PostProcessor;
import com.run.crawler.weibo.processor.proxy.ProcessorProxy;
import com.run.crawler.weibo.processor.writer.Writer;
import com.run.crawler.weibo.rules.impl.ChangeWeiboPageIsStartNowRule;
import com.run.crawler.weibo.rules.impl.DefaultBeforeRule;
import com.run.crawler.weibo.rules.impl.ExtractorOkRule;
import com.run.crawler.weibo.rules.impl.FetcherOkRule;
import com.run.crawler.weibo.rules.impl.NeteaseFollowerHasNextPageRule;
import com.run.crawler.weibo.rules.impl.NeteaseFriendHasNextPageRule;
import com.run.crawler.weibo.rules.impl.NeteaseWeiboHasNextPageRule;
import com.run.crawler.weibo.rules.impl.Nick2AccountIdRule;
import com.run.crawler.weibo.rules.impl.SinaWeiboFromUserTimeLineHasNextPageRule;
import com.run.crawler.weibo.rules.impl.SinaWeiboHasNextPageRule;
import com.run.crawler.weibo.rules.impl.SinaWriterStandardRule;
import com.run.crawler.weibo.rules.impl.SohuFollowerHasNextPageRule;
import com.run.crawler.weibo.rules.impl.SohuFriendHasNextPageRule;
import com.run.crawler.weibo.rules.impl.SohuUserIdRule;
import com.run.crawler.weibo.rules.impl.SohuWeiboHasNextPageRule;
import com.run.crawler.weibo.rules.impl.TencentWeiboFromUserTimeLineHasNextPageRule;
import com.run.crawler.weibo.rules.impl.TencentWeiboHasNextPageRule;
import com.run.crawler.weibo.rules.impl.TencentWriterStandardRule;
import com.run.crawler.weibo.rules.impl.TwitterFollowerHasNextPageRule;
import com.run.crawler.weibo.rules.impl.TwitterFriendHasNextPageRule;
import com.run.crawler.weibo.rules.impl.UpdateNeteaseOauthRule;
import com.run.crawler.weibo.rules.impl.UpdateSinaOauthRule;
import com.run.crawler.weibo.rules.impl.UpdateSohuOauthRule;
import com.run.crawler.weibo.rules.impl.UpdateTencentOauthRule;
import com.run.crawler.weibo.rules.impl.UpdateTwitterOauthRule;
import com.run.crawler.weibo.rules.impl.WriterOkRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Priority;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.VariableCache;

public class Work implements Runnable {

	ProcessorChain processor;
	WeiboPage weiboPage;

	/**
	 * 这部分需要用spring配置
	 */
	{
		/**
		 * 主处理器链 优先级最高
		 */
		processor = new ProcessorChain();
		processor.setPriority(Priority.Tall);

		/**
		 * 下载器 优先级First
		 */
		Fetcher fetcher = new Fetcher();
		ProcessorProxy fetcherProxy = new ProcessorProxy(fetcher);
		fetcherProxy.setPriority(Priority.First);
		fetcherProxy.addBeforeRules(new UpdateTwitterOauthRule());
		fetcherProxy.addBeforeRules(new UpdateSinaOauthRule());
		fetcherProxy.addBeforeRules(new UpdateTencentOauthRule());
		fetcherProxy.addBeforeRules(new UpdateNeteaseOauthRule());
		fetcherProxy.addBeforeRules(new UpdateSohuOauthRule());
		fetcherProxy.addAfterRules(new FetcherOkRule());
		processor.addProcessor(fetcherProxy);

		/**
		 * 解析器链 优先级Second weiboProcessorChain、friendProcessorChain
		 * followerProcessorChain为该链的子链
		 */
		ProcessorChain extractorChain = new ProcessorChain();
		extractorChain.setPriority(Priority.Second);
		/**
		 * weibo子链 优先级First
		 */
		ProcessorChain weiboExtractorChain = new ProcessorChain();
		weiboExtractorChain.setPriority(Priority.First);
		weiboExtractorChain.setJob(Job.Weibo);

		SinaWeiboExtractor sinaWeiboExtractor = new SinaWeiboExtractor();
		ProcessorProxy sinaWeiboExtractorProxy = new ProcessorProxy(
				sinaWeiboExtractor);
		sinaWeiboExtractorProxy.setTask(Task.Sina);
		sinaWeiboExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sinaWeiboExtractorProxy.addAfterRules(new ExtractorOkRule());
		weiboExtractorChain.addProcessor(sinaWeiboExtractorProxy);

		TencentWeiboExtractor tencentWeiboExtractor = new TencentWeiboExtractor();
		ProcessorProxy tencentWeiboExtractorProxy = new ProcessorProxy(
				tencentWeiboExtractor);
		tencentWeiboExtractorProxy.setTask(Task.Tencent);
		tencentWeiboExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		tencentWeiboExtractorProxy.addAfterRules(new ExtractorOkRule());
		weiboExtractorChain.addProcessor(tencentWeiboExtractorProxy);

		TwitterWeiboExtractor twitterWeiboExtractor = new TwitterWeiboExtractor();
		ProcessorProxy twitterWeiboExtractorProxy = new ProcessorProxy(
				twitterWeiboExtractor);
		twitterWeiboExtractorProxy.setTask(Task.Twitter);
		twitterWeiboExtractorProxy.setPriority(Priority.Second);
		twitterWeiboExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterWeiboExtractorProxy.addAfterRules(new ExtractorOkRule());
		weiboExtractorChain.addProcessor(twitterWeiboExtractorProxy);

		/**
		 * 采用userTimeLine方式获取微博数据的处理器链
		 */
		ProcessorChain weiboUserTimeLineExtractorChain = new ProcessorChain();
		weiboUserTimeLineExtractorChain.setPriority(Priority.First);
		weiboUserTimeLineExtractorChain.setJob(Job.WeiboFromUserTimeLine);

		TencentWeiboFromUserTimeLineExtractor tencentWeiboFromUserTimeLineExtractor = new TencentWeiboFromUserTimeLineExtractor();
		ProcessorProxy tencentWeiboFromUserTimeLineExtractorProxy = new ProcessorProxy(
				tencentWeiboFromUserTimeLineExtractor);
		tencentWeiboFromUserTimeLineExtractorProxy.setTask(Task.Tencent);
		tencentWeiboFromUserTimeLineExtractorProxy
				.addBeforeRules(new DefaultBeforeRule());
		tencentWeiboFromUserTimeLineExtractorProxy
				.addAfterRules(new ExtractorOkRule());
		weiboUserTimeLineExtractorChain
				.addProcessor(tencentWeiboFromUserTimeLineExtractorProxy);

		SinaWeiboFromUserTimeLineExtractor sinaWeiboFromUserTimeLineExtractor = new SinaWeiboFromUserTimeLineExtractor();
		ProcessorProxy sinaWeiboFromUserTimeLineExtractorProxy = new ProcessorProxy(
				sinaWeiboFromUserTimeLineExtractor);
		sinaWeiboFromUserTimeLineExtractorProxy.setTask(Task.Sina);
		sinaWeiboFromUserTimeLineExtractorProxy
				.addBeforeRules(new DefaultBeforeRule());
		sinaWeiboFromUserTimeLineExtractorProxy
				.addAfterRules(new ExtractorOkRule());
		weiboUserTimeLineExtractorChain
				.addProcessor(sinaWeiboFromUserTimeLineExtractorProxy);

		SohuWeiboFromUserTimeLineExtractor sohuWeiboFromUserTimeLineExtractor = new SohuWeiboFromUserTimeLineExtractor();
		ProcessorProxy sohuWeiboFromUserTimeLineExtractorProxy = new ProcessorProxy(
				sohuWeiboFromUserTimeLineExtractor);
		sohuWeiboFromUserTimeLineExtractorProxy.setTask(Task.Sohu);
		sohuWeiboFromUserTimeLineExtractorProxy
				.addBeforeRules(new DefaultBeforeRule());
		sohuWeiboFromUserTimeLineExtractorProxy
				.addAfterRules(new ExtractorOkRule());
		weiboUserTimeLineExtractorChain
				.addProcessor(sohuWeiboFromUserTimeLineExtractorProxy);

		NeteaseWeiboFromUserTimeLineExtractor neteaseWeiboFromUserTimeLineExtractor = new NeteaseWeiboFromUserTimeLineExtractor();
		ProcessorProxy neteaseWeiboFromUserTimeLineExtractorProxy = new ProcessorProxy(
				neteaseWeiboFromUserTimeLineExtractor);
		neteaseWeiboFromUserTimeLineExtractorProxy.setTask(Task.Netease);
		neteaseWeiboFromUserTimeLineExtractorProxy
				.addBeforeRules(new DefaultBeforeRule());
		neteaseWeiboFromUserTimeLineExtractorProxy
				.addAfterRules(new ExtractorOkRule());
		weiboUserTimeLineExtractorChain
				.addProcessor(neteaseWeiboFromUserTimeLineExtractorProxy);

		TwitterWeiboFromUserTimelineExtractor twitterWeiboFromUserTimelineExtractor = new TwitterWeiboFromUserTimelineExtractor();
		ProcessorProxy twitterWeiboFromUserTimelineExtractorProxy = new ProcessorProxy(
				twitterWeiboFromUserTimelineExtractor);
		twitterWeiboFromUserTimelineExtractorProxy.setTask(Task.Twitter);
		twitterWeiboFromUserTimelineExtractorProxy.setPriority(Priority.Second);
		twitterWeiboFromUserTimelineExtractorProxy
				.addBeforeRules(new DefaultBeforeRule());
		twitterWeiboFromUserTimelineExtractorProxy
				.addAfterRules(new ExtractorOkRule());
		weiboUserTimeLineExtractorChain
				.addProcessor(twitterWeiboFromUserTimelineExtractorProxy);

		/**
		 * friend子链 优先级Second
		 */
		ProcessorChain friendExtractorChain = new ProcessorChain();
		friendExtractorChain.setPriority(Priority.Second);
		friendExtractorChain.setJob(Job.Friend);

		SinaFriendExtractor sinaFriendExtractor = new SinaFriendExtractor();
		ProcessorProxy sinaFriendExtractorProxy = new ProcessorProxy(
				sinaFriendExtractor);
		sinaFriendExtractorProxy.setTask(Task.Sina);
		sinaFriendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sinaFriendExtractorProxy.addAfterRules(new ExtractorOkRule());
		friendExtractorChain.addProcessor(sinaFriendExtractorProxy);

		TencentFriendExtractor tencentFriendExtractor = new TencentFriendExtractor();
		ProcessorProxy tencentFriendExtractorProxy = new ProcessorProxy(
				tencentFriendExtractor);
		tencentFriendExtractorProxy.setTask(Task.Tencent);
		tencentFriendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		tencentFriendExtractorProxy.addAfterRules(new ExtractorOkRule());
		friendExtractorChain.addProcessor(tencentFriendExtractorProxy);

		TwitterFriendExtractor twitterFriendExtractor = new TwitterFriendExtractor();
		ProcessorProxy twitterFriendExtractorProxy = new ProcessorProxy(
				twitterFriendExtractor);
		twitterFriendExtractorProxy.setTask(Task.Twitter);
		twitterFriendExtractorProxy.setPriority(Priority.Second);
		twitterFriendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterFriendExtractorProxy.addAfterRules(new ExtractorOkRule());
		friendExtractorChain.addProcessor(twitterFriendExtractorProxy);

		NeteaseFriendExtractor neteastFriendExtractor = new NeteaseFriendExtractor();
		ProcessorProxy neteastFriendExtractorProxy = new ProcessorProxy(
				neteastFriendExtractor);
		neteastFriendExtractorProxy.setTask(Task.Netease);
		neteastFriendExtractorProxy.setPriority(Priority.Second);
		neteastFriendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		neteastFriendExtractorProxy.addAfterRules(new ExtractorOkRule());
		friendExtractorChain.addProcessor(neteastFriendExtractorProxy);

		SohuFriendExtractor sohuFriendExtractor = new SohuFriendExtractor();
		ProcessorProxy sohuFriendExtractorProxy = new ProcessorProxy(
				sohuFriendExtractor);
		sohuFriendExtractorProxy.setTask(Task.Sohu);
		sohuFriendExtractorProxy.setPriority(Priority.Second);
		sohuFriendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sohuFriendExtractorProxy.addAfterRules(new ExtractorOkRule());
		friendExtractorChain.addProcessor(sohuFriendExtractorProxy);

		/**
		 * follower子链 优先级Second
		 */
		ProcessorChain followerExtractorChain = new ProcessorChain();
		followerExtractorChain.setPriority(Priority.Second);
		followerExtractorChain.setJob(Job.Follower);

		SinaFollowerExtractor sinaFollowerExtractor = new SinaFollowerExtractor();
		ProcessorProxy sinaFollowerExtractorProxy = new ProcessorProxy(
				sinaFollowerExtractor);
		sinaFollowerExtractorProxy.setTask(Task.Sina);
		sinaFollowerExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sinaFollowerExtractorProxy.addAfterRules(new ExtractorOkRule());
		followerExtractorChain.addProcessor(sinaFollowerExtractorProxy);

		TencentFollowerExtractor tencentFollowerExtractor = new TencentFollowerExtractor();
		ProcessorProxy tencentFollowerExtractorProxy = new ProcessorProxy(
				tencentFollowerExtractor);
		tencentFollowerExtractorProxy.setTask(Task.Tencent);
		tencentFollowerExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		tencentFollowerExtractorProxy.addAfterRules(new ExtractorOkRule());
		followerExtractorChain.addProcessor(tencentFollowerExtractorProxy);

		TwitterFollowerExtractor twitterFollowerExtractor = new TwitterFollowerExtractor();
		ProcessorProxy twitterFollowerExtractorProxy = new ProcessorProxy(
				twitterFollowerExtractor);
		twitterFollowerExtractorProxy.setTask(Task.Twitter);
		twitterFollowerExtractorProxy.setPriority(Priority.Second);
		twitterFollowerExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterFollowerExtractorProxy.addAfterRules(new ExtractorOkRule());
		followerExtractorChain.addProcessor(twitterFollowerExtractorProxy);

		NeteaseFollowerExtractor neteastFollowerExtractor = new NeteaseFollowerExtractor();
		ProcessorProxy neteastFollowerExtractorProxy = new ProcessorProxy(
				neteastFollowerExtractor);
		neteastFollowerExtractorProxy.setTask(Task.Netease);
		neteastFollowerExtractorProxy.setPriority(Priority.Second);
		neteastFollowerExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		neteastFollowerExtractorProxy.addAfterRules(new ExtractorOkRule());
		followerExtractorChain.addProcessor(neteastFollowerExtractorProxy);

		SohuFollowerExtractor sohuFollowerExtractor = new SohuFollowerExtractor();
		ProcessorProxy sohuFollowerExtractorProxy = new ProcessorProxy(
				sohuFollowerExtractor);
		sohuFollowerExtractorProxy.setTask(Task.Sohu);
		sohuFollowerExtractorProxy.setPriority(Priority.Second);
		sohuFollowerExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sohuFollowerExtractorProxy.addAfterRules(new ExtractorOkRule());
		followerExtractorChain.addProcessor(sohuFollowerExtractorProxy);

		/**
		 * UserShow子链 优先级Second
		 */
		ProcessorChain userShowExtractorChain = new ProcessorChain();
		userShowExtractorChain.setPriority(Priority.Second);
		userShowExtractorChain.setJob(Job.UserShow);

		TwitterUserShowExtractor twitterUserShowExtractor = new TwitterUserShowExtractor();
		ProcessorProxy twitterUserShowExtractorProxy = new ProcessorProxy(
				twitterUserShowExtractor);
		twitterUserShowExtractorProxy.setTask(Task.Twitter);
		twitterUserShowExtractorProxy.setPriority(Priority.Second);
		twitterUserShowExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterUserShowExtractorProxy.addAfterRules(new ExtractorOkRule());
		userShowExtractorChain.addProcessor(twitterUserShowExtractorProxy);

		NeteaseUserShowExtractor neteastUserShowExtractor = new NeteaseUserShowExtractor();
		ProcessorProxy neteastUserShowExtractorProxy = new ProcessorProxy(
				neteastUserShowExtractor);
		neteastUserShowExtractorProxy.setTask(Task.Netease);
		neteastUserShowExtractorProxy.setPriority(Priority.Second);
		neteastUserShowExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		neteastUserShowExtractorProxy.addAfterRules(new ExtractorOkRule());
		userShowExtractorChain.addProcessor(neteastUserShowExtractorProxy);

		SohuUserShowExtractor sohuUserShowExtractor = new SohuUserShowExtractor();
		ProcessorProxy sohuUserShowExtractorProxy = new ProcessorProxy(
				sohuUserShowExtractor);
		sohuUserShowExtractorProxy.setTask(Task.Sohu);
		sohuUserShowExtractorProxy.setPriority(Priority.Second);
		sohuUserShowExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		sohuUserShowExtractorProxy.addAfterRules(new ExtractorOkRule());
		userShowExtractorChain.addProcessor(sohuUserShowExtractorProxy);

		/**
		 * Search子链 优先级Second
		 */
		ProcessorChain searchExtractorChain = new ProcessorChain();
		searchExtractorChain.setPriority(Priority.Second);
		searchExtractorChain.setJob(Job.Search);

		TwitterSearchExtractor twitterSearchExtractor = new TwitterSearchExtractor();
		ProcessorProxy twitterSearchExtractorProxy = new ProcessorProxy(
				twitterSearchExtractor);
		twitterSearchExtractorProxy.setTask(Task.Twitter);
		twitterSearchExtractorProxy.setPriority(Priority.Second);
		twitterSearchExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterSearchExtractorProxy.addAfterRules(new ExtractorOkRule());
		searchExtractorChain.addProcessor(twitterSearchExtractorProxy);

		NeteaseSearchExtractor neteastSearchExtractor = new NeteaseSearchExtractor();
		ProcessorProxy neteastSearchExtractorProxy = new ProcessorProxy(
				neteastSearchExtractor);
		neteastSearchExtractorProxy.setTask(Task.Netease);
		neteastSearchExtractorProxy.setPriority(Priority.Second);
		neteastSearchExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		neteastSearchExtractorProxy.addAfterRules(new ExtractorOkRule());
		searchExtractorChain.addProcessor(neteastSearchExtractorProxy);

		/**
		 * Trend子链 优先级Second
		 */
		ProcessorChain trendExtractorChain = new ProcessorChain();
		trendExtractorChain.setPriority(Priority.Second);
		trendExtractorChain.setJob(Job.Trend);

		TwitterTrendExtractor twitterTrendExtractor = new TwitterTrendExtractor();
		ProcessorProxy twitterTrendExtractorProxy = new ProcessorProxy(
				twitterTrendExtractor);
		twitterTrendExtractorProxy.setTask(Task.Twitter);
		twitterTrendExtractorProxy.setPriority(Priority.Second);
		twitterTrendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		twitterTrendExtractorProxy.addAfterRules(new ExtractorOkRule());
		trendExtractorChain.addProcessor(twitterTrendExtractorProxy);

		NeteaseTrendExtractor neteastTrendExtractor = new NeteaseTrendExtractor();
		ProcessorProxy neteastTrendExtractorProxy = new ProcessorProxy(
				neteastTrendExtractor);
		neteastTrendExtractorProxy.setTask(Task.Netease);
		neteastTrendExtractorProxy.setPriority(Priority.Second);
		neteastTrendExtractorProxy.addBeforeRules(new DefaultBeforeRule());
		neteastTrendExtractorProxy.addAfterRules(new ExtractorOkRule());
		trendExtractorChain.addProcessor(neteastTrendExtractorProxy);

		extractorChain.addProcessor(weiboExtractorChain)
				.addProcessor(weiboUserTimeLineExtractorChain)
				.addProcessor(friendExtractorChain)
				.addProcessor(followerExtractorChain)
				.addProcessor(userShowExtractorChain)
				.addProcessor(trendExtractorChain)
				.addProcessor(searchExtractorChain);

		processor.addProcessor(extractorChain);

		/**
		 * 存储器 优先级Third
		 */
		Writer writer = new Writer();
		ProcessorProxy writerProxy = new ProcessorProxy(writer);
		writerProxy.setPriority(Priority.Third);
		writerProxy.addBeforeRules(new SohuUserIdRule());
		writerProxy.addBeforeRules(new DefaultBeforeRule());
		/**
		 * 3.0需要这个rule 2.5不需要 将来要用spring进行配置
		 */
		if (!VariableCache.Controller.isStartFromDB) {
			writerProxy.addBeforeRules(new Nick2AccountIdRule());
		}

		/**
		 * fix CRAWEIBO-37
		 */
		writerProxy.addBeforeRules(new TencentWriterStandardRule());
		writerProxy.addBeforeRules(new SinaWriterStandardRule());
		writerProxy.addAfterRules(new WriterOkRule());

		TencentWeiboHasNextPageRule tencentWeiboHasNextPageRule = new TencentWeiboHasNextPageRule();
		writerProxy.addAfterRules(tencentWeiboHasNextPageRule);

		TencentWeiboFromUserTimeLineHasNextPageRule tencentWeiboFromUserTimeLineHasNextPageRule = new TencentWeiboFromUserTimeLineHasNextPageRule();
		writerProxy.addAfterRules(tencentWeiboFromUserTimeLineHasNextPageRule);

		/*
		 * TencentFriendHasNextPageRule tencentFriendHasNextPageRule = new
		 * TencentFriendHasNextPageRule();
		 * writerProxy.addAfterRules(tencentFriendHasNextPageRule);
		 */

		/*
		 * TencentFollowerHasNextPageRule tencentFollowerHasNextPageRule = new
		 * TencentFollowerHasNextPageRule();
		 * writerProxy.addAfterRules(tencentFollowerHasNextPageRule);
		 */

		SinaWeiboHasNextPageRule sinaWeiboHasNextPageRule = new SinaWeiboHasNextPageRule();
		writerProxy.addAfterRules(sinaWeiboHasNextPageRule);

		SinaWeiboFromUserTimeLineHasNextPageRule sinaWeiboFromUserTimeLineHasNextPageRule = new SinaWeiboFromUserTimeLineHasNextPageRule();
		writerProxy.addAfterRules(sinaWeiboFromUserTimeLineHasNextPageRule);

		/*
		 * SinaFriendHasNextPageRule sinaFriendHasNextPageRule = new
		 * SinaFriendHasNextPageRule();
		 * writerProxy.addAfterRules(sinaFriendHasNextPageRule);
		 */

		/*
		 * SinaFollowerHasNextPageRule sinaFollowerHasNextPageRule = new
		 * SinaFollowerHasNextPageRule();
		 * writerProxy.addAfterRules(sinaFollowerHasNextPageRule);
		 */

		TwitterFriendHasNextPageRule twitterFriendHasNextPageRule = new TwitterFriendHasNextPageRule();
		writerProxy.addAfterRules(twitterFriendHasNextPageRule);

		TwitterFollowerHasNextPageRule twitterFollowerHasNextPageRule = new TwitterFollowerHasNextPageRule();
		writerProxy.addAfterRules(twitterFollowerHasNextPageRule);

		NeteaseWeiboHasNextPageRule neteaseWeiboHasNextPageRule = new NeteaseWeiboHasNextPageRule();
		writerProxy.addAfterRules(neteaseWeiboHasNextPageRule);

		NeteaseFriendHasNextPageRule neteastFriendHasNextPageRule = new NeteaseFriendHasNextPageRule();
		writerProxy.addAfterRules(neteastFriendHasNextPageRule);

		NeteaseFollowerHasNextPageRule neteastFollowerHasNextPageRule = new NeteaseFollowerHasNextPageRule();
		writerProxy.addAfterRules(neteastFollowerHasNextPageRule);

		SohuWeiboHasNextPageRule sohuWeiboHasNextPageRule = new SohuWeiboHasNextPageRule();
		writerProxy.addAfterRules(sohuWeiboHasNextPageRule);

		SohuFollowerHasNextPageRule sohuFollowerHasNextPageRule = new SohuFollowerHasNextPageRule();
		writerProxy.addAfterRules(sohuFollowerHasNextPageRule);

		SohuFriendHasNextPageRule sohuFriendHasNextPageRule = new SohuFriendHasNextPageRule();
		writerProxy.addAfterRules(sohuFriendHasNextPageRule);

		processor.addProcessor(writerProxy);

		/**
		 * 后处理器 优先级Fourth 不需加任何规则，保证一定要被执行
		 */
		PostProcessor postProcessor = new PostProcessor();
		ProcessorProxy postProcessorProxy = new ProcessorProxy(postProcessor);
		postProcessorProxy.setPriority(Priority.Fourth);
		postProcessorProxy.addBeforeRules(new ChangeWeiboPageIsStartNowRule());

		processor.addProcessor(postProcessorProxy);
	}

	public Work(WeiboPage weiboPage) {
		this.weiboPage = weiboPage;
	}

	private void work() {
		synchronized (weiboPage) {
			processor.process(weiboPage);
		}
	}

	@Override
	public void run() {
		work();
	}

}
