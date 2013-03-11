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
	 * ����url
	 */
	private String url;

	/**
	 * ��Ȩ
	 */
	private String oauthUrl;
	/**
	 * ������ʲ���
	 */
	private Query query;

	/**
	 * �ص���Id
	 */
	private long accountId;

	/**
	 * �ص����ʺ� ������2.5
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
	 * ���ص�string����
	 */
	private String content;

	/**
	 * �����ɵ�vecolityBean
	 */
	private List<VelocityBean> velocityBeanList = new ArrayList<VelocityBean>();

	public List<VelocityBean> getVelocityBeanList() {
		return velocityBeanList;
	}

	public void setVelocityBeanList(List<VelocityBean> velocityBeanList) {
		this.velocityBeanList = velocityBeanList;
	}

	/**
	 * ��weiboPage��Queue�е����ȼ�
	 */
	private int priority = 0;

	/**
	 * ��weiboPage��Ӧ��task
	 */
	private Task task;

	/**
	 * ��weiboPage��Ӧ��job
	 */
	private Job job;

	/**
	 * �Ƿ��������� ��������Ҫ��������һ�Σ�֮�����ڴ���
	 */
	private boolean isNew = true;

	/**
	 * ��weiboPage����processor��������е�״̬
	 */
	private State state = new State();

	/**
	 * ���������������ж೤ʱ�� ���ʱ�������Ҫ��int��Ϊlong
	 */
	private int startTime = 0;
	private final int _1s = new Long(TimerUtil._1s).intValue();

	/**
	 * ���������Ѿ��ȴ��˶��
	 */
	private volatile int waitTime = 0;

	/**
	 * �Ƿ񱻴������
	 */
	private boolean isProcessed = true;

	/**
	 * �Ƿ����
	 */
	private boolean isDisAbled = false;

	/**
	 * ��һ�������������Ƿ�ɹ�
	 */
	private boolean isPreProcessOk = false;

	/**
	 * �������
	 */
	private int errorNum = -1;

	/**
	 * ����
	 */
	private Attribute attribute = Attribute.Main;

	/**
	 * ������ΪSun ���ʾ���ڸ���������������� �˴�����Ϊnull���������޵�clone
	 */
	private WeiboPage[] sonWeiboPages = null;

	/**
	 * �Ƿ�����һҳ
	 */
	private boolean hasNextPage = false;

	/**
	 * ���������ز��õķ���
	 */
	private HttpMethod httpMethod = HttpMethod.GET;

	/**
	 * ��������ͷ
	 */
	private Header[] HttpHeader;

	/**
	 * ��Ծ��ĳ������λ��
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
	 * �����´α�ִ�л��ж��
	 * 
	 * @return
	 */
	public int then() {
		return startTime - waitTime;
	}

	/**
	 * ����
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
