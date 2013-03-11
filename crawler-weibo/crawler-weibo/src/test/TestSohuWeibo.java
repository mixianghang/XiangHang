package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestSohuWeibo {

	@SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("test/a.txt"))));
		StringBuilder sb = new StringBuilder();
		String s = "";
		while ((s = bufferedReader.readLine()) != null) {
			sb.append(s);
		}
		bufferedReader.close();
		
		
    JSONArray array =  JSONArray.fromObject(sb.toString());
    for (int i = 0; i < array.size(); i++) {
      try {
        VelocityBean bean = VelocityBeanPool.borrowObject();
        JSONObject weibo = array.getJSONObject(i);
        JSONObject user = (JSONObject) weibo.get("user");
        String weiboID = weibo.getString("id");
        String text = weibo.getString("text");
        String screenName = user.getString("screen_name");
        String name = user.getString("name");
        String publishDate =weibo.getString("created_at");
        String userID = user.getString("id");
        String source = weibo.getString("source");
        String fansnum = user.getString("followers_count");
        String idolnum = user.getString("friends_count");
        
        /**
         * 是否包含转发内容
         */
        if(weibo.containsKey("retweeted_status")) {
          JSONObject retweeted = weibo.getJSONObject("retweeted_status");
          String OriText = retweeted.getString("text");
          bean.setOrigtext(OriText);
          System.out.println("OriText=" + OriText);
        }
        
        System.out.println("weiboID=" + weiboID);
        System.out.println("text=" + text);
        System.out.println("screenName=" + screenName);
        System.out.println("name=" + name);
        System.out.println("publishDate=" + publishDate);
        System.out.println("userID=" + userID);
        System.out.println("source=" + source);
        System.out.println("*******************************************\n");
       
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }
    }
	}
}
