package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestTencentWeibo {

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

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");


		JSONArray infoJSONArray = dataJSONObject.getJSONArray("info");

		if (infoJSONArray.isEmpty())
			return;

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();
		while (iterator.hasNext()) {
			JSONObject json = iterator.next();
			String text = json.getString("text");
			String count = json.getString("count");
			String mcount = json.getString("mcount");
			String fromurl = json.getString("fromurl");
			String id = json.getString("id");
			String image = json.getString("image");
			String openid = json.getString("openid");
			String nick = json.getString("nick");
			String self = json.getString("self");
			String timestamp = json.getString("timestamp");
			String type = json.getString("type");
			String location = json.getString("location");
			String emotionurl = json.getString("emotionurl");
			String origtext = "";
			if (json.containsKey("source")) {
				JSONObject j = json.getJSONObject("source");
				origtext = j.getString("text");
			}

			System.out.println("text=" + text);
			System.out.println("origtext=" + origtext);
			System.out.println("count=" + count);
			System.out.println("mcount=" + mcount);
			System.out.println("fromurl=" + fromurl);
			System.out.println("id=" + id);
			System.out.println("image=" + image);
			System.out.println("openid=" + openid);
			System.out.println("nick=" + nick);
			System.out.println("self=" + self);
			System.out.println("timestamp = "+timestamp);
			System.out.println("type=" + type);
			System.out.println("location=" + location);
			System.out.println("emotionurl=" + emotionurl);
			System.out.println("*******************************************\n");
		}
		
	}
}
