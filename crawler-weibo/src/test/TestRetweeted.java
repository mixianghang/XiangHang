package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;

public class TestRetweeted {

	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File("conf/a.txt"))));
		StringBuilder sb = new StringBuilder();
		String s = "";
		while ((s = bufferedReader.readLine()) != null) {
			sb.append(s);
		}
		bufferedReader.close();
		JSONArray array = JSONArray.fromObject(sb.toString());
		for (int i = 0; i < array.size(); i++) {
			try {
				VelocityBean bean = VelocityBeanPool.borrowObject();
				JSONObject weibo = array.getJSONObject(i);
				JSONObject user = (JSONObject) weibo.get("user");
				String weiboID = weibo.getString("id");
				String text = weibo.getString("text");
				String screenName = user.getString("screen_name");
				String name = user.getString("name");
				String userID = user.getString("id");
				String rcount = weibo.getString("retweet_count");
				String source = weibo.getString("source");
				String geo = weibo.getString("geo");
				String fansnum = user.getString("followers_count");
				String idolnum = user.getString("friends_count");
				String publishDate = weibo.getString("created_at");
				bean.setAccount(screenName);
				bean.setId(weiboID);
				bean.setName(name);
				bean.setNickName(screenName);
				bean.setOpenid(userID);
				bean.setText(text);
				bean.setCount(rcount);
				bean.setFromurl(source);
				if (geo != null && !"".equals(geo)) {
					bean.setGeo(geo);
				}
				/**
				 * 是否包含转发内容
				 */
				if (weibo.containsKey("retweeted_status")) {
					JSONObject retweeted = weibo
							.getJSONObject("retweeted_status");
					String OriText = retweeted.getString("text");
					bean.setOrigtext(OriText);
					System.out.println("OriText=" + OriText);
				}
				bean.setFansnum(fansnum);
				bean.setIdolnum(idolnum);

				System.out.println("screenName=" + screenName);
				System.out.println("text=" + text);
				System.out.println("publishDate=" + publishDate);

				System.out
						.println("*******************************************\n");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
