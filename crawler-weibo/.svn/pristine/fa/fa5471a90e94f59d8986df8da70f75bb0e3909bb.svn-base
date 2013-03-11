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

public class TestTwitterFriend {

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
		
		
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		JSONArray users = jsonObject.getJSONArray("users");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String relationtime = f.format(date);
		String nextCursor = jsonObject.getString("next_cursor");
		for (int i = 0; i < users.size(); i++) {
			JSONObject user = users.getJSONObject(i);
			VelocityBean bean = VelocityBeanPool.borrowObject();
			String screenName = user.getString("screen_name");
			String name = user.getString("name");
			String userID = user.getString("id");
			String location = user.getString("location");
			String fansnum = user.getString("followers_count");
			String idolnum = user.getString("friends_count");

			System.out.println("nextCursor=" + nextCursor);
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
