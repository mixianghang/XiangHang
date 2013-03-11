package com.run.crawler.weibo.util;

/**
 * 关注/取消关注错误码
 * TODO
 * @author  zhangzhen
 * @version $Revision: $, $Id: $
 * 0:成功   1：失败
 * 子错误码说明[1:关注失败 ，2：取消关注失败]
 */
public enum AttentionCode {
  SUCESS("0"),ERROR11("11"),ERROR12("12");
  
  String code;
  private AttentionCode(String code){
    this.code = code;
  }
}
