package td;

import java.util.HashMap;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import td.other.MyUtil;
import td.task.LoginTdService;
import td.task.OrderService;

public class Main extends Application {

	boolean maxTaskFlag=true;
	boolean tTaskFlag = true;
	boolean gRun = true;//网络验证全局
	int tv_iid=1;
	boolean fristRun =true;
	String timestamp="";
	HashMap<String,String> accountMap = new HashMap<String,String>();
	HashMap<String,String> myaccountMap = new HashMap<String,String>();


	Button bLoginTD = new Button("登录");
	Button b_login = new Button("登录");
	Button b_order = new Button("抢单");
	Button b_pause = new Button("暂停");
	Button b_mylogin = new Button("登录");

	Label  l_myaccount = new Label("软件账号:");
	Label  l_mypassword = new Label("软件密码:");
	TextField t_myaccount =  new TextField();
	TextField t_mypassword =  new TextField();

	Label  l_account = new Label("账号:");
	Label  l_password = new Label("密码:");
	TextField t_account =  new TextField();
	TextField t_password =  new TextField();

	Label  l_price= new Label("价格:");
	Label  l_bro = new Label("佣金:");
	Label  l_reftime = new Label("刷间:");
	Label  l_ifeige = new Label("信码:");
	TextField t_price =  new TextField();
	TextField t_bro =  new TextField();
	TextField t_reftime =  new TextField();
	TextField t_ifeige =  new TextField();
	CheckBox c_voice = new CheckBox("语音提示");
	CheckBox c_voice1 = new CheckBox("高佣优先");
	CheckBox c_voice2 = new CheckBox("检测假报");

	ComboBox<String> com_accounts =new ComboBox<String>();
	ComboBox<String> com_myaccounts =new ComboBox<String>();





	Label l_state = new Label("状态:请登录赚客平台");

	Label l_state1 = new Label("运行时间:0s");
	Label l_state2 = new Label("企鹅群:730352171,自动搬砖软件");

	ObservableList<String> namelist = FXCollections.observableArrayList();

	ObservableList<String> mynamelist = FXCollections.observableArrayList();

	TableView<OrderData> tv = new TableView<OrderData>();
	TableColumn<OrderData, Number> tv_id = new TableColumn<OrderData, Number>("序号");
	TableColumn<OrderData, String> tv_orderId = new TableColumn<OrderData, String>("订单编号");
	TableColumn<OrderData, Number> tv_price = new TableColumn<OrderData, Number>("价格");
	TableColumn<OrderData, Number> tv_brokerage = new TableColumn<OrderData, Number>("佣金");
	TextArea ta = new TextArea();










	@Override
	public void start(Stage primaryStage) throws Exception {

		AnchorPane ap = new AnchorPane();
		ap.getChildren().addAll(bLoginTD);












		Scene scene = new Scene(ap,590,960);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/img/icon.png"));
		primaryStage.setTitle("TDFast...1.0.2");
		primaryStage.setScene(scene);
		primaryStage.show();

		//以下为数据初始化=======================================================================
		//以下为本地服务初始化=======================================================================
		LoginTdService lts = new LoginTdService();
		OrderService os = new OrderService();




		//以下为按键监听=======================================================================
		bLoginTD.setOnAction(new EventHandler<ActionEvent>() {
			//人工登录平台获取cookies
			@Override
			public void handle(ActionEvent event) {
				MyUtil.setGlobalPropertyG("account", "杨森5270");
				if(MyUtil.getLocalCookies()){
					print(MyUtil.getGlobalPropertyG("Cookies"));
					//TODO 解封其他动作按钮2 调用一个函数
					os.start();
					return;
				}
				Alert al = new Alert(AlertType.INFORMATION);
				al.setContentText("请在弹出的浏览器中登录淘单账号，登录后等待软件自动操作");
				al.show();
				lts.start();
			}
		});


		//以下为service的监听=========================================================================
		lts.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pintToA(newValue);
			}
		});
		lts.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue!=null){
					if(newValue.intValue()==1){
						//TODO 解封其他动作按钮1 调用一个函数

					}
				}

			}
		});


	}


	public static void main(String[] args) {
		launch(args);
	}


	public void print(Object msg){
		if(msg==null){
			return;
		}
		System.out.println(this.getClass().getName()+"->"+msg.toString());
	}

	public void pintToA(Object o){
		if(o==null){return;}
		System.out.println(o.toString());
	}



}
