package com.run.crawler.weibo.util.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.AccountOperator;
import com.run.crawler.weibo.frontier.Frontier;
import com.run.crawler.weibo.util.Task;

public class AccountDBCache implements AccountCacheInterface {

	static Logger log = Logger.getLogger(AccountDBCache.class);

	private static String host = VariableCache.AccountDBCache.host;
	private static String name = VariableCache.AccountDBCache.name;
	private static String password = VariableCache.AccountDBCache.password;

	private List<Account> last = new ArrayList<Account>();
	private List<Account> successAccounts = new ArrayList<Account>();

	private Frontier frontier;

	private AccountDBCache() {

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

				if (last.size() == 0) {
					log.info("初始启动...........");
					/**
					 * 初始启动
					 */
					last.addAll(getAll());
					return;
				}

				successAccounts.clear();

				List<Account> list = getAll();
				for (Account account : list) {
					/**
					 * 增加
					 */
					if (!last.contains(account)) {
						account.setOperator(AccountOperator.Add);
						successAccounts.add(account);
					}
				}
				for (Account account : last) {
					/**
					 * 删除
					 */
					if (!list.contains(account)) {
						account.setOperator(AccountOperator.Delete);
						successAccounts.add(account);
					}
				}

				log.info("successAccounts.size : " + successAccounts.size());

				last.clear();
				last.addAll(list);

				notifyFrontier();
			}
		}, 5 * 60 * 1000L, 5 * 60 * 1000L);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AccountDBCache().getAll();
	}

	/**
	 * 通知Frontier更改任务
	 */
	private void notifyFrontier() {
		if (successAccounts.size() > 0) {
			frontier.updateWeiboPage(successAccounts);
			successAccounts.clear();
		}
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	private static Connection getConnection() {
		log.info("host : " + host);
		log.info("name : " + name);
		log.info("password : " + password);
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + host
					+ ":1521:orcl", name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 加载缓存中所有记录
	 * 
	 * @return
	 */
	public List<Account> getAll() {
		log.info("getAll into ...........");
		List<Account> list = new ArrayList<Account>();

		String queryString = VariableCache.AccountDBCache.queryString;
		String sql = "select uuid,account,category from keypersonaccount";
		if (!"".equals(queryString)) {
			sql = sql.concat(" where ");
			sql = sql.concat(queryString);
		}
		sql = sql.concat(" order by uuid desc");

		log.info("sql = " + sql);

		Connection con = getConnection();
		Statement stateMent = null;
		ResultSet resultSet = null;
		try {
			log.info("con = " + con);
			stateMent = con.createStatement();
			resultSet = stateMent.executeQuery(sql);
			while (resultSet.next()) {
				Account account = new Account();
				account.setAccountId(resultSet.getLong(1));
				account.setNickName(resultSet.getString(2));
				account.setTask(Task.getTask(resultSet.getInt(3)));
				list.add(account);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.info(e.getMessage());
				}
			}
			if (null != stateMent) {
				try {
					stateMent.close();
				} catch (SQLException e) {
					log.info(e.getMessage());
				}
			}
			if (null != con) {
				try {
					con.close();
				} catch (SQLException e) {
					log.info(e.getMessage());
				}
			}
		}
		log.info("list.size() = " + list.size());
		log.info("getAll end ............");
		return list;
	}

	private static class Proxy {
		private static AccountDBCache accountDBCache = new AccountDBCache();
	}

	public static AccountDBCache newInstance() {
		return Proxy.accountDBCache;
	}

	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

}
