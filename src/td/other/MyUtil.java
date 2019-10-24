package td.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MyUtil {




	public static HashMap<String, Object> globalMap = new HashMap<String, Object>();
	public static List<String> blackList = new ArrayList<String>();
	public static List<String> usedList = new ArrayList<String>();

	public static boolean taskRun = true;
	public static HashMap<String, String> fixedHead(){
		HashMap<String,String> headers = new HashMap<String,String>();
	    headers.put("Host", "053666.cn");
	    headers.put("Connection", "keep-alive");
	    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36");
	    headers.put("Accept-Encoding", "gzip, deflate");
	    headers.put("Accept-Language", "zh,zh-HK;q=0.9,zh-CN;q=0.8,en;q=0.7,zh-TW;q=0.6");
	    return headers;

	}

	public static void setTaskRun(boolean b){
		taskRun = b;
	}
	public static boolean getTaskRun(){
		return taskRun;
	}
	public static void setGlobalPropertyG(String key ,Object o){
		globalMap.put(key,o);
	}

	public static Object getGlobalPropertyG(String key){
	    try {
	        Object o = globalMap.get(key);
	        return o;
	    } catch (Exception e) {
	        return null;
	    }

	}
	public static void addBlackList(String orderid){

		orderid = getRealid(orderid);
		if(blackList.contains(orderid)){
			return;
		}
		blackList.add(orderid);
		addBlackIdToFile(orderid);
	}

	public static void addUsedList(String orderid){
		orderid = getRealid(orderid);
		if(usedList.contains(orderid)){
			return;
		}
		usedList.add(orderid);
		addUserdIdToFile(orderid);

	}

	public static String getRealid(String orderid){

		return orderid.substring(6,11);
	}
	public static void addUserdIdToFile(String orderid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + "Used.txt";
	    isExistsFile(path, true);
	    FileOutputStream out;
		try {
			out = new FileOutputStream(new File(path),true);
			PrintStream p = new PrintStream(out);
	        p.append(orderid+"|");
	        p.flush();
	        out.flush();
	        p.close();
	        out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addBlackIdToFile(String orderid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + "Black.txt";
	    isExistsFile(path, true);
	    FileOutputStream out;
		try {
			out = new FileOutputStream(new File(path),true);
			PrintStream p = new PrintStream(out);
	        p.append(orderid+"|");
	        p.flush();
	        out.flush();
	        p.close();
	        out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void setUserdIdFileToUserList(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + "Used.txt";
	    isExistsFile(path, true);

	    FileReader fr;
		try {
			fr = new FileReader(new File(path));
			char[] chars = new char[20000];
			int num = fr.read(chars);
			String str;
			if(num==-1){
				str="";
			}else{
				str = new String(chars,0,num);
			}

			if(str.equals("")){
				return;
			}
			String[] strs = str.split("\\|");
			for(int i = 0;i<strs.length;i++){
				if(!strs[i].equals("")){
					usedList.add(strs[i]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	public static void setBlackNameFileToBlackList(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + "Black.txt";
	    isExistsFile(path, true);

	    FileReader fr;
		try {
			fr = new FileReader(new File(path));
			char[] chars = new char[20000];
			int num = fr.read(chars);
			String str;
			if(num==-1){
				str="";
			}else{
				str = new String(chars,0,num);
			}

			if(str.equals("")){
				return;
			}
			String[] strs = str.split("\\|");
			for(int i = 0;i<strs.length;i++){
				if(!strs[i].equals("")){
					blackList.add(strs[i]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}



	}
	public static List getBlackList(){
		return blackList;
	}
	public static boolean setLocalInfo(HashMap<String,String> infomap){
		print("setLocalInfo->arg1  infomap->"+infomap.toString());
		String path = "D:\\td\\userInfo.txt";
		JSONObject jsonObject =  (JSONObject)JSON.toJSON(infomap);
		try {
			JSON.writeJSONString(new FileWriter(new File(path)), jsonObject, SerializerFeature.EMPTY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void setAccounts(HashMap<String,String> map){
		//System.out.println(map);
	    if(map==null){
	    	return;
	    }
	    String text="";
	    Set keys = map.keySet();
		Iterator iterator = keys.iterator();

		while(iterator.hasNext()){
			String key = iterator.next().toString();
			String txt = map.get(key);
			text = text+key+"-"+txt+"|";

		}
	    String path = "D:\\td\\accounts.txt";
	    isExistsFile(path, true);
	    //System.out.println("setAccounts->"+text);
	    FileWriter out;
		try {
			out = new FileWriter(new File(path));
			//intStream p = new PrintStream(out);
	        out.write(text);
	        out.flush();
	        out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap<String,String> getAccounts(){
		print("getLocalInfo->");
		//String path = "D:\\td\\userInfo.txt";
		String path= "D:\\td\\accounts.txt";
		HashMap<String,String> accounts = new HashMap<String,String>();
		isExistsFile(path,true);
		FileReader fr;
		try {
			fr = new FileReader(new File(path));
			char[] chars = new char[1024];
			int num = fr.read(chars);
			if(num==-1){
				return accounts;
			}
			String str = new String(chars,0,num);
			String[] strs =  str.split("\\|");
			for(String s:strs){
				String[] strs1 =  s.split("-");
				accounts.put(strs1[0],strs1[1]);
			}
			return accounts;


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static HashMap<String,String>getLocalInfo(){
		print("getLocalInfo->");
		HashMap<String,String> userinfo = new HashMap<String,String>();
		String path = "D:\\td\\userInfo.txt";
		//String path1= "D:\\td\\accounts.txt";
		isExistsFile(path,true);
		FileReader fr;
		try {
			fr = new FileReader(new File(path));
			char[] chars = new char[1024];
			int num = fr.read(chars);
			if(num==-1){
				if(userinfo.get("price")==null){
					print("price null");
					userinfo.put("price", "50");
				}

				if(userinfo.get("brokerage")==null){
					print("brokerage null");
					userinfo.put("brokerage", "1");
				}

				if(userinfo.get("ifeige")==null){
					print("ifeige null");
					userinfo.put("ifeige", "0000");
				}

				if(userinfo.get("reftime")==null){
					print("reftime null");
					userinfo.put("reftime", "3000-6000");
				}
				return userinfo;
			}
			String str = new String(chars,0,num);
			JSONObject jo = (JSONObject)JSON.parse(str);
			Set keys = jo.keySet();
			Iterator iterator = keys.iterator();

			while(iterator.hasNext()){
				String key = iterator.next().toString();
				userinfo.put(key, jo.get(key).toString());
			}

			if(userinfo.get("price")==null){
				print("price null");
				userinfo.put("price", "50");
			}

			if(userinfo.get("brokerage")==null){
				print("brokerage null");
				userinfo.put("brokerage", "1");
			}

			if(userinfo.get("ifeige")==null){
				print("ifeige null");
				userinfo.put("ifeige", "0000");
			}

			if(userinfo.get("reftime")==null){
				print("reftime null");
				userinfo.put("reftime", "3000-6000");
			}





			return userinfo;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;

	}

	public static boolean setLocalCookies(HashMap cookie) {
	    print("setLocalCookies->arg1 cookie " + cookie);
	    setGlobalPropertyG("Cookies", cookie);
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lo = new Date().getTime()+ (24 * 60 * 60 * 1000);
        Date date = new Date(lo);
        String str = sdf1.format(date);
        str += "qazwsx";
        Set keySet = cookie.keySet();
        Iterator iterator = keySet.iterator();
        print("setLocalCookies->arg1 cookie 111");
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            str += key + "=" + cookie.get(key).toString() + "|";
        }
        print("setLocalCookies->arg1 cookie 22222");
        str = str.substring(0, str.length() - 1);

        DBUtil.updateCookies(getGlobalPropertyG("account").toString(), str);
        print("setLocalCookies->arg1 cookie 333");
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + ".txt";
	    return writeFile(path, str);
	}

	public static boolean getLocalCookies() {
	    print("getLocalCookies->start");
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String name = "nothing";
	    if(getGlobalPropertyG("account")!=null){
	    	name = getGlobalPropertyG("account").toString();
	    }
	    if(name.equals("")){
	    	name="nothing";
	    }
	    String path = "D:\\td\\" + name + sdf.format(new Date()).toString() + ".txt";
	    isExistsFile(path,true);
	    String cookiesStr = getFileText(path, false);
	    if (cookiesStr.equals("nothing")) {
	        print("getLocalCookies->local cookies is not exist");
	        return false;
	    } else {
	        String[] cookiesStrs = cookiesStr.split("qazwsx");
	        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lo=Long.MIN_VALUE;
			try {
				lo = sdf1.parse(cookiesStrs[0]).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if (isTimeoutOfCookies(lo)){
	            return false;
	        } else {
	            HashMap<String,String> cookie = new HashMap<String,String>();
	            String str = cookiesStrs[1];
	            String[] strs = str.split("\\|");
	            print("getLocalCookies-> 登录cookies数->" + strs.length);
	            for (int i = 0; i < strs.length; i++) {
	                String[] strs1 = strs[i].split("=");
	                cookie.put(strs1[0], strs1[1]);
	            }
	            setGlobalPropertyG("Cookies", cookie);
	            return true;
	        }
	    }
	}

	public static String getFileText(String path, boolean iscreateNewFile) {
	    File file = new File(path);
	    if (isExistsFile(path, false)) {

	    	FileReader fr;
			try {
				fr = new FileReader(new File(path));
				char[] chars = new char[2048];
				//TODO 这里只读了2048个字节。以后改成都全部
				int num = fr.read(chars);
				if(num==-1){
					return "nothing";
				}
				return new String(chars,0,num);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "nothing";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "nothing";
			}

	    } else {
	        return "nothing";
	    }

	}
	public static boolean isTimeoutOfCookies(long times) {
	    if (times - new Date().getTime() < 60 * 60 * 1000) {
	        print("isTimeoutOfCookies->true");
	        return true;
	    }
	    print("isTimeoutOfCookies->false");
	    return false;
	}
	public static void print(String msg){
		System.out.println(MyUtil.class.getName()+"->"+msg);
	}
	public static boolean writeFile(String path, String text) {
	    if (isExistsFile(path, true)) {
	    	try{
	    		File file = new File(path);
		        FileWriter fw = new FileWriter(file);
		        fw.write(text);
		        fw.flush();
		        fw.close();
		        return true;

	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return false;
	    	}

	    }
	    return false;
	}
	public static boolean isExistsFile(String path, boolean iscreateNewFile) {
	    File file = new File(path);

	    if (file.exists()) {
	        print("isExistsFile->" + path + " 存在");
	        return true;

	    } else {
	        if (iscreateNewFile) {
	            if (!file.getParentFile().exists()) {
	                print("getFileText->" + path + " file.getParentFile().mkdirs():" + file.getParentFile().mkdirs());

	            }
	            try {
					print("getFileText->" + path + " createNewFile:" + file.createNewFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
	            return true;
	        } else {
	            return false;
	        }
	    }
	}

}
