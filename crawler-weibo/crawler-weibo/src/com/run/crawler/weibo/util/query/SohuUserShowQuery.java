package com.run.crawler.weibo.util.query;


public class SohuUserShowQuery extends Query{
	
  private String count;
  private String cursor;
  private String nick_name;
  
  public String getCount() {
    return count;
  }
  public void setCount(String count) {
    this.count = count;
  }
  public String getCursor() {
    return cursor;
  }
  public void setCursor(String cursor) {
    this.cursor = cursor;
  }
  public String getNick_name() {
    return nick_name;
  }
  public void setNick_name(String nick_name) {
    this.nick_name = nick_name;
  }
}
