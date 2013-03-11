package com.run.crawler.weibo.entity;

import org.apache.commons.codec.digest.DigestUtils;

import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

/**
 * ʵ����Cloneable�ӿ� ����������������
 * 
 * @author jerry
 * 
 */
public class VelocityBean implements Cloneable {

	private Task task;
	private Job job;

	/**
	 * Ϊ����������ļ�
	 */
	private int category = 0;

	/**
	 * �ص����˻���
	 */
	private String account = "";

	/**
	 * �ص��������ݿ��е�id
	 */
	private long accountId = 0L;

	/**
	 * �������ʻ���
	 */
	private String name = "";

	/**
	 * �������ǳ�
	 */
	private String nickName = "";

	/**
	 * ������Ψһid
	 */
	private String openid = "";

	/**
	 * ΢������
	 */
	private String text = "";

	/**
	 * ԭʼ����
	 */
	private String origtext = "";

	/**
	 * ת����
	 */
	private String count = "";

	/**
	 * ������
	 */
	private String mcount = "";

	/**
	 * ΢����ԴURL
	 */
	private String fromurl = "";

	/**
	 * ΢��Ψһid
	 */
	private String id = "";

	/**
	 * ͼƬURL�б�
	 */
	private String image = "";

	/**
	 * �Ƿ��Լ�����
	 */
	private String self = "";

	/**
	 * ����ʱ��
	 */
	private String timestamp = "";

	/**
	 * ΢������
	 */
	private String type = "";

	/**
	 * ���������ڵ�
	 */
	private String location = "";

	/**
	 * �����ߵ�����Ϣ
	 */
	private String geo = "";

	/**
	 * ����ͼƬURL
	 */
	private String emotionurl = "";

	/**
	 * �Ƿ�����һҳ
	 */
	private String hasNext = "";

	/**
	 * ����
	 */
	private String description = "";

	/**
	 * ����&��˿
	 */

	/**
	 * ͷ���б�
	 */
	private String head = "";

	/**
	 * �Ա�
	 */
	private String sex = "";

	/**
	 * ��˿��
	 */
	private String fansnum = "";

	/**
	 * ������
	 */
	private String idolnum = "";

	/**
	 * �Ƿ���֤�û�
	 */
	private String isvip = "";

	/**
	 * �������
	 */
	/**
	 * ����ؼ���
	 */
	private String trendName = "";

	/**
	 * �����ؼ���
	 */
	private String trendKeywords = "";

	/**
	 * ����id
	 */
	private String trendId = "";

	/**
	 * ���ⱻ�ղش���
	 */
	private String trendFavnum = "";

	/**
	 * ������΢����
	 */
	private String trendTweetnum = "";

	/**
	 * md5ֵ��Ϊ��ȥ��
	 */
	private String md5 = "";

	/**
	 * @return the trendName
	 */
	public String getTrendName() {
		return trendName;
	}

	/**
	 * @param trendName
	 *            the trendName to set
	 */
	public void setTrendName(String trendName) {
		this.trendName = trendName;
	}

	/**
	 * @return the trendKeywords
	 */
	public String getTrendKeywords() {
		return trendKeywords;
	}

	/**
	 * @param trendKeywords
	 *            the trendKeywords to set
	 */
	public void setTrendKeywords(String trendKeywords) {
		this.trendKeywords = trendKeywords;
	}

	/**
	 * @return the trendId
	 */
	public String getTrendId() {
		return trendId;
	}

	/**
	 * @param trendId
	 *            the trendId to set
	 */
	public void setTrendId(String trendId) {
		this.trendId = trendId;
	}

	/**
	 * @return the trendFavnum
	 */
	public String getTrendFavnum() {
		return trendFavnum;
	}

	/**
	 * @param trendFavnum
	 *            the trendFavnum to set
	 */
	public void setTrendFavnum(String trendFavnum) {
		this.trendFavnum = trendFavnum;
	}

	/**
	 * @return the trendTweetnum
	 */
	public String getTrendTweetnum() {
		return trendTweetnum;
	}

	/**
	 * @param trendTweetnum
	 *            the trendTweetnum to set
	 */
	public void setTrendTweetnum(String trendTweetnum) {
		this.trendTweetnum = trendTweetnum;
	}

	/**
	 * ��ȡʱ��
	 */
	private String relationtime = "";

	public String getRelationtime() {
		return relationtime;
	}

	public void setRelationtime(String relationtime) {
		this.relationtime = relationtime;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFansnum() {
		return fansnum;
	}

	public void setFansnum(String fansnum) {
		this.fansnum = fansnum;
	}

	public String getIdolnum() {
		return idolnum;
	}

	public void setIdolnum(String idolnum) {
		this.idolnum = idolnum;
	}

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getEmotionurl() {
		return emotionurl;
	}

	public void setEmotionurl(String emotionurl) {
		this.emotionurl = emotionurl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrigtext() {
		return origtext;
	}

	public void setOrigtext(String origtext) {
		this.origtext = origtext;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getMcount() {
		return mcount;
	}

	public void setMcount(String mcount) {
		this.mcount = mcount;
	}

	public String getFromurl() {
		return fromurl;
	}

	public void setFromurl(String fromurl) {
		this.fromurl = fromurl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getMd5() {
		return md5;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * md5ǰ��λ�ֱ����task��job ��֤sina��tencent������������ͻ
	 * 
	 * @param strings
	 */
	public void genMd5(String... strings) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.task.getI());
		sb.append(this.job.getI());
		for (String str : strings) {
			sb.append(str);
		}
		this.md5 = DigestUtils.md5Hex(sb.toString());
	}

	public void clear() {
		this.md5 = this.name = this.nickName = "";
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsvip() {
		return isvip;
	}

	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
