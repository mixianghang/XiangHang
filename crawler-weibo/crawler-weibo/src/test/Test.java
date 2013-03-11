package test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

public class Test{
	@SuppressWarnings("unused")
	private String name = "jerry";
	/*public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	private String pass;
	
	
	public static void main(String[] args) throws ParseException{
		@SuppressWarnings("unused")
		String s = "http://weibo.com/jerry3g?code=48e9abd6df94046fb67ec1054a8dd591";
		
		HttpGet get = new HttpGet("aa");
		get.addHeader(new BasicHeader("a", "b"));
		get.addHeader(new BasicHeader("a", "b"));
		for(Header header : get.getAllHeaders())
		System.out.println(header.getName() + ":" + header.getValue());
				
				 SimpleDateFormat format1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					 SimpleDateFormat format2 = new SimpleDateFormat(
							"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
					 
					 System.out.println(format1.format(format2.parse("Tue May 31 17:46:55 +0800 2011")));
		System.out.println(new File("./").getAbsolutePath());
		System.out.println("-1".equals(-1));
		
		System.out.println(System.getProperty("user.dir"));
		
	}
}

