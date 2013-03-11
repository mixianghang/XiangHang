package com.run.crawler.weibo.entity;

import java.io.File;

import com.run.crawler.weibo.util.Task;

public class Account {

	/**
	 * 微博账户Id
	 */
	private String userId;

	/**
	 * 帐号来源
	 */
	private Task task;
	/**
	 * accountId
	 */
	private long accountId;
	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 账户操作
	 */
	private AccountOperator operator = AccountOperator.Default;

	/**
	 * 重点人对应的文件名称
	 */
	private File file;

	/**
	 * 关注、取消失败次数
	 */
	private int failedNo;

	/**
	 * 重点人账号顺序
	 */
	private int order;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getFailedNo() {
		return failedNo;
	}

	public void setFailedNo(int failedNo) {
		this.failedNo = failedNo;
	}

	public AccountOperator getOperator() {
		return operator;
	}

	public void setOperator(AccountOperator operator) {
		this.operator = operator;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

}
