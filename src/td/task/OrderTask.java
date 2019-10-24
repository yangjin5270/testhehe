package td.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.stream.FileImageInputStream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javafx.concurrent.Task;
import td.other.MyUtil;

public class OrderTask extends Task<Number> {


	public int state1;
	public double price;
	public double brokerage;
	public int minreftime;
	public int maxreftime;

	boolean flag=true;



	public OrderTask() {
		super();

	}


	public OrderTask(double price, double brokerage, int minreftime, int maxreftime) {
		super();
		this.price = price;
		this.brokerage = brokerage;
		this.minreftime = minreftime;
		this.maxreftime = maxreftime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(double brokerage) {
		this.brokerage = brokerage;
	}


	public int getMinreftime() {
		return minreftime;
	}


	public void setMinreftime(int minreftime) {
		this.minreftime = minreftime;
	}

	public int getMaxreftime() {
		return maxreftime;
	}


	public void setMaxreftime(int maxreftime) {
		this.maxreftime = maxreftime;
	}



	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	@Override
	protected Number call() throws Exception {
		this.updateMessage("开始抢单");

		/*while(true){*/
			print("state1->"+state1);
			while(MyUtil.getTaskRun()==false){
					this.updateMessage("暂停中......");
					Thread.sleep(3000);

			}
			switch(state1){

				case 6:
					processorHaveTask();
					break;
				case 7:
					processorHaveAlertTask();
					break;
				case 8:
					processorHaveTask();
					break;
				case 9:
					processorMaxTask();
					break;
				default:

					break;
			}
			state1 = -1;
			print("条件最高垫资："+this.price+"最低佣金："+this.brokerage+"刷新频率："+this.maxreftime);
			getOrderList();
			Thread.sleep(new Random().nextInt(maxreftime-minreftime)+minreftime);
			return 1;

		/*}*/

	}

	public void processorMaxTask(){
		this.updateMessage("已超过当日最大接单量");
		while(true){
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void processorHaveAlertTask(){
		int i = 0;
		while(true){
			try {
				this.updateMessage("有商家提醒收货，请收货后再接单<"+i+">");
				i++;
				Thread.sleep(60000);
				if(!getAlertTaskList()){

					return;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void processorHaveTask(){
		int i = 0;
		while(true){
			try {
				//当前有任务未提交，请先完成任务再接单
				this.updateMessage("当前有任务未提交，请先完成任务再接单<"+i+">");
				i++;
				Thread.sleep(60000);
				if(!getTaskList()){

					return;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	public boolean getAlertTaskList() {

	    print("getAlertTaskList->start");

	    List taskList = new ArrayList();
	    HashMap<String,String> headers = new HashMap<String,String>();
	    headers = MyUtil.fixedHead();
	    headers.put("Upgrade-Insecure-Requests", "1");
	    headers.put("Cache-Control", "max-age=0");
	    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
	    headers.put("Referer", "http://zk.gxrdwl.com/index.php/main/task_mem/tlist/mv/3/p/2.html");

	    HashMap cookies = new HashMap();
	    cookies = (HashMap) MyUtil.getGlobalPropertyG("Cookies");
	    Connection.Response response=null;
		try {
			response = Jsoup.connect("http://zk.gxrdwl.com/index.php/Main/TaskMem/tlist?mv=9")
			        .ignoreHttpErrors(true)
			        .ignoreContentType(true)
			        .method(Connection.Method.GET)
			        .timeout(30000)
			        .headers(headers)
			        .cookies(cookies)
			        .execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	    String bodyRStr = response.body();
	    //printPro(bodyRStr);
	    if (bodyRStr.contains("反爬虫代码")) {
	        //TODO getTaskList()可能出现反爬虫js代码，后期跟进判断
	        //未获取到待付款任务
	        return true;
	    } else if(bodyRStr.contains("创宇云安全")){
	        print("getAlertTaskList->response.body()->包含创宇云安全");

	        return true;
	    } else {
	        Document document = Jsoup.parse(bodyRStr);
	        Element element = document.selectFirst("#ywlb > ul > li:nth-child(6) > a > div > span");

	        if(Integer.valueOf(element.text())==0){

	            return false;
	        }

	        return true;
	    }

	}

	public boolean getTaskList() {

	    print("getTaskList->start");

	    List taskList = new ArrayList();
	    HashMap<String,String> headers = new HashMap<String,String>();
	    headers = MyUtil.fixedHead();
	    headers.put("Upgrade-Insecure-Requests", "1");
	    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
	    headers.put("Referer", "http://zk.gxrdwl.com/index.php/Main/Center/index");

	    HashMap cookies = new HashMap();
	    cookies = (HashMap) MyUtil.getGlobalPropertyG("Cookies");
	    Connection.Response response=null;
		try {
			response = Jsoup.connect("http://zk.gxrdwl.com/index.php/Main/TaskMem/wlist")
			        .ignoreHttpErrors(true)
			        .ignoreContentType(true)
			        .method(Connection.Method.GET)
			        .timeout(30000)
			        .headers(headers)
			        .cookies(cookies)
			        .execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String bodyRStr = response.body();
	    //printPro(bodyRStr);
	    if (bodyRStr.contains("反爬虫代码")) {
	        //TODO getTaskList()可能出现反爬虫js代码，后期跟进判断
	        //未获取到待付款任务
	        return true;
	    } else if(bodyRStr.contains("创宇云安全")){
	        print("getTaskList->start->response.body()->包含创宇云安全");

	        return true;
	    } else {
	        Document document = Jsoup.parse(bodyRStr);
	        Elements elements = document.getElementsByAttributeValue("colspan", "6");

	        for (int i = 0; i < elements.size(); i++) {
	            Element element = elements.get(i);
	            if (element.text().toString().equals("")) {
	                print("getTaskList->Html 格式正确，未获取到待付款任务，退出");
	                return false;
	            }

	        }

	        return true;
	    }

	}



	public void getOrderList(){

		print(">获取任务大厅列表--------------");
	    HashMap<String,String> headers = new HashMap<String,String>();
	    headers = MyUtil.fixedHead();
	    headers.put("Upgrade-Insecure-Requests", "1");
	    headers.put("Referer", "http://053666.cn/user/index.aspx");
	    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b30");
	    String url="http://www.053666.cn/user/newtasklist.aspx";
	    try {
			Connection.Response response = Jsoup.connect(url)
			        .ignoreContentType(true)
			        .ignoreHttpErrors(true)
			        .headers(headers)
			        .cookies((HashMap<String,String>)MyUtil.getGlobalPropertyG("Cookies"))
			        .timeout(30000)
			        .method(Connection.Method.GET)
			        .execute();
			print("====================================================================================");
			print(response.body());
			Document doc = Jsoup.parse(response.body());
			Elements orderidels = doc.getElementsByAttributeValue("class", "pic");
			Elements priels = doc.getElementsByAttributeValue("class", "w110 orange cloq1 f18 fb");
			Elements broels = doc.getElementsByAttributeValue("class", "w110 orange cloq2 f16 fb");
			Elements idels = doc.getElementsByAttributeValue("value", "立即抢单");


			if(flag){
				flag=false;
				this.updateMessage("订单总数("+orderidels.size()+")");
			}else{
				flag = true;
				this.updateMessage("订单总数("+orderidels.size()+")     ");
			}


			for(int i=0;i<orderidels.size();i++){

				String orderid = null,pri,bro,id = null;



				Element e = orderidels.get(i);
				Pattern p = Pattern.compile("[0-9]{24}");
				Matcher m = p.matcher(e.text());

				if(m.find()){
					orderid = m.group();
				}
				String realorderid = MyUtil.getRealid(orderid);
				if(MyUtil.getBlackList().contains(realorderid)||MyUtil.usedList.contains(realorderid)){
					//print("二次限制名单，商家限制二次接单天数间隔，目前无法接该单");或者接过的订单不在接
					continue;
				}



				e = priels.get(i);
				double d = Double.valueOf(e.text().substring(1,e.text().length()));
				if(d>price){
					continue;
				}


				e = broels.get(i);
				double d1 = Double.valueOf(e.text().substring(1,e.text().length()));
				if(d1<brokerage){
					continue;
				}

				e = idels.get(i);
				String str = e.attr("onclick");
				p = Pattern.compile("[0-9]{7,9}");
				m = p.matcher(str);
				if(m.find()){
					id = m.group();
				}


				//print(orderid+"  "+id+"  "+d+"  "+d1);
				//new Thread(new getOrderRunnable(id,orderid,String.valueOf(d),String.valueOf(d1))).start();



			}







		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void print(Object msg){
		System.out.println(this.getClass().getName()+"->"+msg.toString());
	}



	class pagingOrderRunable  implements Runnable{


		public String pageid;
		public pagingOrderRunable(String pageid) {
			super();
			this.pageid = pageid;
		}
		@Override
		public void run() {
			print(">获取任务分页列表--------------");
		    HashMap<String,String> headers = new HashMap<String,String>();
		    headers = MyUtil.fixedHead();
		    headers.put("Cache-Control", "max-age=0");
		    headers.put("Upgrade-Insecure-Requests", "1");
		    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b30");
		    String url="http://zk.gxrdwl.com/index.php/Main/TaskMain/main/pmv/71366";
		    try {
				Connection.Response response = Jsoup.connect(url)
				        .ignoreContentType(true)
				        .ignoreHttpErrors(true)
				        .headers(headers)
				        .cookies((HashMap<String,String>)MyUtil.getGlobalPropertyG("Cookies"))
				        .timeout(30000)
				        .method(Connection.Method.GET)
				        .execute();
				Document doc = Jsoup.parse(response.body());
				Elements orderidels = doc.getElementsByAttributeValue("class", "pic");
				Elements priels = doc.getElementsByAttributeValue("class", "w110 orange cloq1 f18 fb");
				Elements broels = doc.getElementsByAttributeValue("class", "w110 orange cloq2 f16 fb");
				Elements idels = doc.getElementsByAttributeValue("value", "立即抢单");
				if(flag){
					flag=false;
					OrderTask.this.updateMessage("订单总数("+orderidels.size()+")");
				}else{
					flag = true;
					OrderTask.this.updateMessage("订单总数("+orderidels.size()+")     ");
				}


				for(int i=0;i<orderidels.size();i++){

					String orderid = null,pri,bro,id = null;



					Element e = orderidels.get(i);
					Pattern p = Pattern.compile("[0-9]{24}");
					Matcher m = p.matcher(e.text());

					if(m.find()){
						orderid = m.group();
					}
					String realorderid = MyUtil.getRealid(orderid);
					if(MyUtil.getBlackList().contains(realorderid)||MyUtil.usedList.contains(realorderid)){
						//print("二次限制名单，商家限制二次接单天数间隔，目前无法接该单");或者接过的订单不在接
						continue;
					}



					e = priels.get(i);
					double d = Double.valueOf(e.text().substring(1,e.text().length()));
					if(d>price){
						continue;
					}


					e = broels.get(i);
					double d1 = Double.valueOf(e.text().substring(1,e.text().length()));
					if(d1<brokerage){
						continue;
					}

					e = idels.get(i);
					String str = e.attr("onclick");
					p = Pattern.compile("[0-9]{7,9}");
					m = p.matcher(str);
					if(m.find()){
						id = m.group();
					}


					//print(orderid+"  "+id+"  "+d+"  "+d1);
					new Thread(new getOrderRunnable(id,orderid,String.valueOf(d),String.valueOf(d1))).start();



				}







			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class getOrderRunnable implements Runnable{

		String id;
		String orderid;
		String pri;
		String bro;
		public getOrderRunnable(String id,String oderid,String pri,String bro) {
			super();
			this.id = id;
			this.orderid = oderid;
			this.pri = pri;
			this.bro = bro;
		}
		@Override
		public void run() {
			OrderTask.this.updateMessage("抢任务编号<"+orderid+">订单");
			HashMap<String,String> headers = new HashMap<String,String>();
		    headers = MyUtil.fixedHead();
		    headers.put("X-Requested-With", "XMLHttpRequest");
		    headers.put("Accept", "*/*");
		    headers.put("Referer", "http://zk.gxrdwl.com/index.php/Main/TaskMain/main/pmv/71366");
		    String url="http://zk.gxrdwl.com/index.php/Main/TaskMain/doTask?id="+id;

		    try {
				Connection.Response response = Jsoup.connect(url)
				        .ignoreContentType(true)
				        .ignoreHttpErrors(true)
				        .headers(headers)
				        .cookies((HashMap<String,String>)MyUtil.getGlobalPropertyG("Cookies"))
				        .timeout(30000)
				        .method(Connection.Method.GET)
				        .execute();
				String rbodystr = response.body();
				if(rbodystr.contains("\\u5df2\\u88ab\\u5176\\u5b83\\u8bd5\\u5ba2\\u62a2\\u8d70\\u4e86")){
					OrderTask.this.updateMessage("任务编号<"+orderid+">订单,已被其它试客抢走了");
					return;
				}else if(rbodystr.contains("\\u6709\\u5546\\u5bb6\\u63d0\\u9192\\u6536\\u8d27\\uff0c\\u8bf7\\u6536\\u8d27\\u540e\\u518d\\u63a5\\u5355")){
					//有商家提醒收货，请收货后再接单
					OrderTask.this.updateMessage("有商家提醒收货，请收货后再接单");
					state1=7;
					return;
				}else if(rbodystr.contains("\\u7531\\u4e8e\\u5546\\u5bb6\\u9650\\u5236\\u4e8c\\u6b21\\u63a5\\u5355\\u5929\\u6570")){
					//由于商家限制二次接单天数间隔，目前无法接该单
					OrderTask.this.updateMessage("任务编号<"+orderid+">订单由于商家限制二次接单天数间隔，目前无法接该单");
					MyUtil.addBlackList(orderid);
					return;
				}else if(rbodystr.contains("\\u5f53\\u524d\\u6709\\u4efb\\u52a1\\u672a\\u63d0\\u4ea4\\uff0c\\u8bf7\\u5148\\u5b8c")){
					//\u5f53\u524d\u6709\u4efb\u52a1\u672a\u63d0\u4ea4\uff0c\u8bf7\u5148\u5b8c\u6210\u4efb\u52a1\u518d\u63a5\u5355
					//当前有任务未提交，请先完成任务再接单
					OrderTask.this.updateMessage("当前有任务未提交，请先完成任务再接单");
					state1 = 6;
					MyUtil.addBlackList(orderid);
					return;
				}else if(rbodystr.contains("success")){
					JSONObject job = JSON.parseObject("{\"state\":\"success\",}");
					job.put("orderid", orderid);
					job.put("pri", pri);
					job.put("bro", bro);
					MyUtil.addUsedList(orderid);
					OrderTask.this.updateTitle(job.toJSONString());
					//OrderTask.this.updateMessage(job.toJSONString());
					state1=8;
					return;
				}else if(rbodystr.contains("\\u5df2\\u8d85\\u8fc7\\u5f53\\u65e5\\u6700\\u5927\\u63a5\\u5355\\u91cf")){
					//\u5df2\u8d85\u8fc7\u5f53\u65e5\u6700\u5927\u63a5\u5355\u91cf 已超过当日最大接单
					OrderTask.this.updateMessage("已超过当日最大接单量");
					MyUtil.setTaskRun(false);
					state1=9;
					return;
				}else if(rbodystr.contains("\\u60a8\\u5df2\\u88ab\\u8be5\\u5546\\u5bb6")){
					//您已被该商家拉入黑名单              \\u60a8\\u5df2\\u88ab\\u8be5\\u5546\\u5bb6\\u62c9\\u5165\\u9ed1\\u540d\\u5355
					OrderTask.this.updateMessage("您已被该商家拉入黑名单");
					MyUtil.addBlackList(orderid);
					return;

				}
				System.out.println("未记录到的字符-》"+rbodystr);


				//Document doc = Jsoup.parse(response.body());


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			OrderTask.this.updateMessage("getOrderRunnablesuccess");

		}
	}

}
