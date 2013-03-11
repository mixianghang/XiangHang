package com.run.crawler.weibo.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import com.run.crawler.weibo.util.Attribute;
import com.run.crawler.weibo.util.HttpMethod;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Priority;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.TimerUtil;
import com.run.crawler.weibo.util.query.Query;

public class WeiboPage implements Cloneable {

	/**
	 * 任务url
	 */
	private String url;

	/**
	 * 授权
	 */
	private String oauthUrl;
	/**
	 * 任务访问参数
	 */
	private Query query;

	/**
	 * 重点人Id
	 */
	private long accountId;

	/**
	 * 重点人帐号 服务于2.5
	 */
	private String account = "";

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 下载的string内容
	 */
	private String content;

	/**
	 * 解析成的vecolityBean
	 */
	private List<VelocityBean> velocityBeanList = new ArrayList<VelocityBean>();

	public List<VelocityBean> getVelocityBeanList() {
		return velocityBeanList;
	}

	public void setVelocityBeanList(List<VelocityBean> velocityBeanList) {
		this.velocityBeanList = velocityBeanList;
	}

	/**
	 * 该weiboPage在Queue中的优先级
	 */
	private int priority = 0;

	/**
	 * 该weiboPage对应的task
	 */
	private Task task;

	/**
	 * 该weiboPage对应的job
	 */
	private Job job;

	/**
	 * 是否是新任务 新任务需要尽快启动一次，之后按周期处理
	 */
	private boolean isNew = true;

	/**
	 * 该weiboPage经过processor处理过程中的状态
	 */
	private State state = new State();

	/**
	 * 距离任务启动还有多长时间 如果时间过长需要将int改为long
	 */
	private int startTime = 0;
	private final int _1s = new Long(TimerUtil._1s).intValue();

	/**
	 * 距离启动已经等待了多久
	 */
	private volatile int waitTime = 0;

	/**
	 * 是否被处理完成
	 */
	private boolean isProcessed = true;

	/**
	 * 是否可用
	 */
	private boolean isDisAbled = false;

	/**
	 * 上一个处理器处理是否成功
	 */
	private boolean isPreProcessOk = false;

	/**
	 * 错误次数
	 */
	private int errorNum = -1;

	/**
	 * 属性
	 */
	private Attribute attribute = Attribute.Main;

	/**
	 * 若属性为Sun 则表示属于该任务的所有子任务 此处必须为null，避免无限的clone
	 */
	private WeiboPage[] sonWeiboPages = null;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage = false;

	/**
	 * 该任务下载采用的方法
	 */
	private HttpMethod httpMethod = HttpMethod.GET;

	/**
	 * 任务请求头
	 */
	private Header[] HttpHeader;

	/**
	 * 跳跃至某处理器位置
	 */
	private Priority jumpTo = Priority.First;

	public Priority getJumpTo() {
		return jumpTo;
	}

	public void setJumpTo(Priority jumpTo) {
		this.jumpTo = jumpTo;
	}

	public Header[] getHttpHeader() {
		return HttpHeader;
	}

	public void setHttpHeader(Header... httpHeader) {
		HttpHeader = httpHeader;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public WeiboPage[] getSonWeiboPages() {
		return sonWeiboPages;
	}

	public void setSonWeiboPages(WeiboPage... sonWeiboPages) {
		this.sonWeiboPages = sonWeiboPages;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int seconds) {
		this.startTime = seconds * _1s;
	}

	public void plusWaitTime(int plus) {
		this.waitTime += (plus * _1s);
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime * _1s;
	}

	/**
	 * 距离下次被执行还有多久
	 * 
	 * @return
	 */
	public int then() {
		return startTime - waitTime;
	}

	/**
	 * 重置
	 */
	public void reset() {
		this.setWaitTime(0);
		this.velocityBeanList.clear();
		this.setContent("");
		this.setHasNextPage(false);
		this.setState(new State());
		this.setJumpTo(Priority.First);
		this.setPreProcessOk(false);
		this.setProcessed(true);
		this.setHttpHeader();
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public boolean isDisAbled() {
		return isDisAbled;
	}

	public void setDisAbled(boolean isDisAbled) {
		this.isDisAbled = isDisAbled;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public boolean isPreProcessOk() {
		return isPreProcessOk;
	}

	public void setPreProcessOk(boolean isPreProcessOk) {
		this.isPreProcessOk = isPreProcessOk;
	}

	@Override
	public Object clone() {
		try {
			WeiboPage sonWeiboPage = (WeiboPage) super.clone();
			Query query = this.getQuery();
			sonWeiboPage.setQuery((Query) query.clone());
			sonWeiboPage.setNew(false);
			return sonWeiboPage;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeiboPage other = (WeiboPage) obj;
		if (job != other.job)
			return false;
		if (task != other.task)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String s = "\r\n";
		sb.append("AccoundId : ").append(this.getAccountId()).append(s);
		sb.append("Task : ").append(this.getTask()).append(s);
		sb.append("Job : ").append(this.getJob()).append(s);
		sb.append("URL : ").append(this.getQuery().toString()).append(s);
		sb.append("Then : ").append(this.then()).append(" MS").append(s);
		sb.append("Attribute : ").append(this.getAttribute()).append(s);
		sb.append("isProcessed : ").append(this.isProcessed);
		return sb.toString();
	}

	public String getOauthUrl() {
		return oauthUrl;
	}

	public void setOauthUrl(String oauthUrl) {
		this.oauthUrl = oauthUrl;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
