package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestSohuFriend {

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
		
		
		JSONObject json = JSONObject.fromObject(sb.toString());
    JSONArray users = json.getJSONArray("users");
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String relationtime = f.format(date);
    /**
     * 下一页ID的游标位置
     */
    String nextCursor = json.getString("cursor_id");
    for(int i=0; i<users.size(); i++) {
      JSONObject user = users.getJSONObject(i);
      VelocityBean bean = VelocityBeanPool.borrowObject();
      String screenName = user.getString("screen_name");
      String name = user.getString("name");
      String userID = user.getString("id");
      String location = user.getString("location");
      String description = user.getString("description");
      String fansnum = user.getString("followers_count");
      String idolnum = user.getString("friends_count");
      String verified = user.getString("verified");
      
      System.out.println("screenName=" + screenName);
      System.out.println("name=" + name);
      System.out.println("userID=" + userID);
      System.out.println("location=" + location);
      System.out.println("followers=" + fansnum);
      System.out.println("friends=" + idolnum);
      System.out.println("md5=" + bean.getMd5());
      System.out.println("*********************************************************");

    }
    
	}
}
