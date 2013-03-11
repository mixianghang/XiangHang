package com.run.crawler.weibo.util.query;


public class SohuWeiboQuery extends Query{

  private String nick_name;
  private int page = 1;
  private String count = "50";

  
  
  public String getNick_name() {
	return nick_name;
}
public void setNick_name(String nick_name) {
	this.nick_name = nick_name;
}
public int getPage() {
    return page;
  }
  public void setPage(int page) {
    this.page = page;
  }
  public String getCount() {
    return count;
  }
  public void setCount(String count) {
    this.count = count;
  }

  
  
}
