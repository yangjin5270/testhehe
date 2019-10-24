package td;

import java.text.SimpleDateFormat;
import java.util.HashMap;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
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





		//界面处理化==================================================================
		HBox hbox0 =new HBox(10.0);
		VBox vbo = new VBox(10);
		VBox vb1 = new VBox(10);
		Tooltip tip1=new Tooltip();
		tip1.setText("联系作者获取软件账号信息");//设置Tooltip内容
		tip1.setFont(new Font("Arial", 16));//设置Tooltip内容字体
		t_myaccount.setTooltip(tip1);
		vbo.getChildren().addAll(l_myaccount,l_mypassword);
		com_myaccounts.setPrefWidth(160);
		vb1.getChildren().addAll(com_myaccounts,t_mypassword);
		Label tep = new Label();
		tep.setPrefWidth(180);
		tep.setPrefHeight(100);
		ImageView iv1 = new ImageView("https://u.ifeige.cn/wechat/share-qrcode/yangjin5270");
		iv1.setFitHeight(100);
		iv1.setFitWidth(100);
		iv1.setLayoutX(600);
		Tooltip tip=new Tooltip();
		tip.setText("微信扫描二维码，关注公众号，联系作者获取微信信息提醒飞鸽代号");//设置Tooltip内容
		tip.setFont(new Font("Arial", 16));//设置Tooltip内容字体
		Tooltip.install(iv1, tip);
		vbo.setAlignment(Pos.CENTER_LEFT);
		vb1.setAlignment(Pos.CENTER_LEFT);
		hbox0.setStyle("-fx-background-image:url('/img/bg.jpg')");
		hbox0.getChildren().addAll(tep,vbo,vb1,b_mylogin,iv1);
		hbox0.setAlignment(Pos.CENTER_LEFT);
		HBox hbox1 =new HBox(10.0);
		hbox1.setAlignment(Pos.CENTER);
		b_login.setPrefWidth(60.0);
		b_order.setPrefWidth(60.0);
		b_pause.setPrefWidth(60.0);
		com_accounts.setPrefWidth(140);
		t_password.setPrefWidth(140);
		b_order.setDisable(true);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.getChildren().addAll(l_account,com_accounts,l_password,t_password,b_login,b_order,b_pause);

		HBox hbox2 =new HBox(10.0);
		Tooltip tt4 = new Tooltip("抢垫资最高不超过设置得的价格的任务");
		tt4.setFont(new Font("Arial", 16));
		t_price.setTooltip(tt4);
		Tooltip tt5 = new Tooltip("抢佣金最低不超过设置的价格的任务");
		tt5.setFont(new Font("Arial", 16));
		t_bro.setTooltip(tt5);
		Tooltip tt6 = new Tooltip("刷新任务大厅的间隔时间(毫秒),格式:1000-6000");
		tt6.setFont(new Font("Arial", 16));
		t_reftime.setTooltip(tt6);
		Tooltip tt7 = new Tooltip("微信扫右上角二维码,获取代码，填入");
		tt7.setFont(new Font("Arial", 16));
		t_ifeige.setTooltip(tt7);
		t_price.setPromptText("抢垫资最高不超过设置得的价格的任务");
		t_bro.setPromptText("抢佣金最低不超过设置的价格的任务");
		t_reftime.setPromptText("刷新任务间隔时间(毫秒),格式:1000-6000");
		t_ifeige.setPromptText("飞鸽代码");
		t_price.setPrefWidth(80);
		t_bro.setPrefWidth(80);
		t_ifeige.setPrefWidth(80);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(l_price,t_price,l_bro,t_bro,l_ifeige,t_ifeige,l_reftime,t_reftime);

		HBox hbox3 =new HBox(10.0);
		Tooltip tt1 =new Tooltip("选择，抢单成功，播放声音");
		tt1.setFont(new Font("Arial", 16));
		c_voice.setTooltip(tt1);
		Tooltip tt2 =new Tooltip("选择，优先抢高佣金 任务");
		tt2.setFont(new Font("Arial", 16));
		c_voice1.setTooltip(tt2);
		Tooltip tt3 =new Tooltip("选择，会检测是否真的抢到任务");
		tt3.setFont(new Font("Arial", 16));
		c_voice2.setTooltip(tt3);
		hbox3.getChildren().addAll(c_voice,c_voice1,c_voice2);
		hbox3.setAlignment(Pos.CENTER_LEFT);

		HBox hbox4 = new HBox(10.0);
		hbox4.setAlignment(Pos.CENTER_LEFT);
		hbox4.getChildren().addAll(l_state,l_state1,l_state2);
		l_state.setFont(Font.font(14));
		l_state1.setFont(Font.font(14));
		l_state1.setStyle("-fx-background-color:#FF34B3");
		l_state2.setFont(Font.font(14));
		l_state2.setStyle("-fx-background-color:#EEAEEE");

		VBox box = new VBox(10.0);
		box.setLayoutX(10);
		box.setLayoutY(5);

		tv.setPrefHeight(260);
		ta.setPrefHeight(200);
		box.getChildren().addAll(hbox0,hbox1,hbox2,hbox3,tv,ta,hbox4);

		AnchorPane ap = new AnchorPane();
		ap.getChildren().addAll(box);
		Scene scene = new Scene(ap,590,700);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/img/icon.png"));
		primaryStage.setTitle("TDFast...1.0.2");
		primaryStage.setScene(scene);
		primaryStage.show();
		//界面初始化完毕==================================================================

		//以下为数据状态初始化=======================================================================
		b_login.setDisable(true);
		b_order.setDisable(true);
		b_pause.setDisable(true);
		//以下为数据状态初始化完毕=======================================================================

		//以下为本地服务初始化=======================================================================
		LoginTdService lts = new LoginTdService();
		OrderService os = new OrderService();




		//以下为按键监听=======================================================================
		b_login.setOnAction(new EventHandler<ActionEvent>() {
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

	public void pause_action(){
		b_order.setDisable(false);
		b_pause.setDisable(true);
	}
	public void run_action(){
		b_order.setDisable(true);
		b_pause.setDisable(false);
	}

	public void pintToA(Object msg){

		if(msg==null||msg.equals("")){return;}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ta.appendText(sdf.format(System.currentTimeMillis())+"->"+msg.toString()+"\n");
		ta.setScrollTop(Double.MAX_VALUE);
	}



}
