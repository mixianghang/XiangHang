package com.run.crawler.weibo.util.oauth.util;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayedOauth implements Delayed, OauthInterface {

  public enum Type {
    READ, WRITE
  }

  private Oauth oauth = null;
  private  final long _hour = 1000 * 60 * 60L;
  private  final long _day = 24 * _hour;
  private long delayTime = this._hour + now();
  private Type type = Type.READ;
  private  final int MAX = 2;
  private AtomicInteger remainTimesIn24h = new AtomicInteger(MAX);
  public AtomicInteger getUsedTimesIn24h() {
    return remainTimesIn24h;
  }
  private long lastUsedTime = 0L;
  
  public DelayedOauth(Oauth oauth) {
    this.oauth = oauth;
  }

  /**
   * 当前对象为队列中的对象，Delayed o为当前需要插入队列中的对象
   */
  @Override
  public int compareTo(Delayed o) {
    DelayedOauth d = (DelayedOauth) o;
    long l = this.getDelayTime() - d.getDelayTime();
    if (l > 0) {
      return 1;
    }
    if (l < 0) {
      return -1;
    }
    return 0;
  }
  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(delayTime - now(), TimeUnit.MILLISECONDS);
  }

  @Override
  public String oauthUrl() {
    return oauth.oauthUrl();
  }

  private long now() {
    return System.currentTimeMillis();
  }

  public long getDelayTime() {
    return delayTime;
  }

  public void setDelayTime(long delayTime) {
    this.delayTime = delayTime;
  }

  /**
   * 重置过期时间
   */
  public void reset() {
    switch (type) {
    case WRITE:
        /**
         * 判断授权是否在24小时内使用
         */
        boolean in24Hour = now() - lastUsedTime < _day ? true : false;
        lastUsedTime = now();
        if (in24Hour) {
          /**
           * 授权24小时内前Max-1次使用
           */
          if (remainTimesIn24h.decrementAndGet() > 0) {
            this.setDelayTime(this._hour + lastUsedTime);
            
            /**
             * 授权24小时内最后一次使用
             */
          } else {
            remainTimesIn24h = new AtomicInteger(MAX);
            this.setDelayTime(this._day + lastUsedTime);
          }
        } else {
          remainTimesIn24h = new AtomicInteger(MAX);
          remainTimesIn24h.decrementAndGet();
          this.setDelayTime(this._hour + lastUsedTime);
        }
        break;
    case READ:
        this.setDelayTime(this._hour + now());
        break;
    default:
        break;
    }
  }

  public Oauth getOauth() {
    return this.oauth;
  }
  public void setType(Type type) {
    this.type = type;
  }
}
