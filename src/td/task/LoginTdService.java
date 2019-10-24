package td.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import td.other.MyUtil;

public class LoginTdService extends Service<Number>{
	@Override
	protected Task<Number> createTask() {
		return new MyTask();
	}

	class MyTask extends Task<Number>{

		@Override
		protected Number call() throws Exception {
			WebDriver driver = new ChromeDriver();
	        driver.get("http://www.053666.cn/land1.aspx");
	        while(true){
	        	 String context = driver.getPageSource();
	    		 String url = driver.getCurrentUrl();
	    		 if(url.contains("/user/index.aspx")){
	    			 print("找到url转到 /user/index.aspx");
	    			 break;
	    		 }
	    		 if(context.equals("我的邀请记录")){
	    			 print("找到页面内容<我的邀请记录>");
	    			 break;
	    		 }
	        	 try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	        }

	        HashMap<String,String> cookies = new HashMap<String,String>();

	        Set<Cookie> set = driver.manage().getCookies();
	        Iterator<Cookie> it = set.iterator();
	        while(it.hasNext()){

	        	Cookie coo = (Cookie)it.next();
	        	String name = coo.getName();
	        	String value = coo.getValue();
	        	cookies.put(name, value);

	        }
	        print(cookies);
	        driver.close();
	        this.updateMessage("数据开始写入云端");
	        MyUtil.setGlobalPropertyG("account", "杨森5270");
	        MyUtil.setLocalCookies(cookies);
	        this.updateMessage("数据写入云端完成");
			return 1;
		}

	}
	public  void print(Object msg){
		System.out.println(this.getClass().getName()+"->"+msg.toString());
	}

}
