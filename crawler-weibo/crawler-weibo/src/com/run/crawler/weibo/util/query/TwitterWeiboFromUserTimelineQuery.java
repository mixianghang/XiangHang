package com.run.crawler.weibo.util.query;


public class TwitterWeiboFromUserTimelineQuery extends Query{
	
  private String screen_name;
  private int count = 20;
  
  
  
  public String getScreen_name() {
    return screen_name;
  }

  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }


}
