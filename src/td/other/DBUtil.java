package td.other;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DBUtil {



	public static long login(String name, String password, String timestamp) {
		String url = "http://106.13.188.41:8080/DBAP/ZkLoginQ?name=" + name + "&password=" + password + "&timeStamp="
				+ timestamp;
		org.jsoup.Connection.Response response = null;
		try {
			response = Jsoup.connect(url).timeout(2000).ignoreContentType(true)
					.ignoreHttpErrors(true).method(org.jsoup.Connection.Method.GET).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject object = (JSONObject) JSON.parse(response.body());
		if (object.getString("state").equals("error")) {
			if (object.getString("state").equals("’À∫≈ªÚ√‹¬Î¥ÌŒÛ£°")) {
				return -2;
			}
			return -1;
		}
		if (object.getString("state").equals("success")) {
			String time = object.getString("msg");
			System.out.println(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lo = Long.valueOf(time);
			System.out.println(sdf.format(new Date(lo)));
			return lo;
		}
		return -1;

	}

	public static boolean verfiy(String timestamp) {
		// List list=[]
		String url = "http://106.13.188.41:8080/DBAP/ZkVerifyQ?timeStamp=" + timestamp;
		org.jsoup.Connection.Response response = null;
		try {
			response = Jsoup.connect(url).timeout(2000).ignoreContentType(true).ignoreHttpErrors(true)
					.method(org.jsoup.Connection.Method.GET).execute();
			JSONObject object = (JSONObject) JSON.parse(response.body());
			System.out.println(response.body());
			if (object.getString("state").equals("-1")) {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	public static boolean updateCookies(String account,String cookies) {

        String url="http://106.13.188.41:8080/DBAP/TDUpdateCookies";
        String rbody="account="+account+"&cookies="+cookies;
		try {

			org.jsoup.Connection.Response res =  Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).method(Method.POST).requestBody(rbody).timeout(5000).execute();
			String str  = res.body();
			System.out.println(str);
			JSONObject jsob =  (JSONObject) JSON.parse(res.body());
			if(jsob.getString("state").equals("success")){
				return true;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }





}
