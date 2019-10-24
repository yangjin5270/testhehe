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
	boolean gRun = true;//������֤ȫ��
	int tv_iid=1;
	boolean fristRun =true;
	String timestamp="";
	HashMap<String,String> accountMap = new HashMap<String,String>();
	HashMap<String,String> myaccountMap = new HashMap<String,String>();


	Button bLoginTD = new Button("��¼");
	Button b_login = new Button("��¼");
	Button b_order = new Button("����");
	Button b_pause = new Button("��ͣ");
	Button b_mylogin = new Button("��¼");

	Label  l_myaccount = new Label("����˺�:");
	Label  l_mypassword = new Label("�������:");
	TextField t_myaccount =  new TextField();
	TextField t_mypassword =  new TextField();

	Label  l_account = new Label("�˺�:");
	Label  l_password = new Label("����:");
	TextField t_account =  new TextField();
	TextField t_password =  new TextField();

	Label  l_price= new Label("�۸�:");
	Label  l_bro = new Label("Ӷ��:");
	Label  l_reftime = new Label("ˢ��:");
	Label  l_ifeige = new Label("����:");
	TextField t_price =  new TextField();
	TextField t_bro =  new TextField();
	TextField t_reftime =  new TextField();
	TextField t_ifeige =  new TextField();
	CheckBox c_voice = new CheckBox("������ʾ");
	CheckBox c_voice1 = new CheckBox("��Ӷ����");
	CheckBox c_voice2 = new CheckBox("���ٱ�");

	ComboBox<String> com_accounts =new ComboBox<String>();
	ComboBox<String> com_myaccounts =new ComboBox<String>();





	Label l_state = new Label("״̬:���¼׬��ƽ̨");

	Label l_state1 = new Label("����ʱ��:0s");
	Label l_state2 = new Label("���Ⱥ:730352171,�Զ���ש���");

	ObservableList<String> namelist = FXCollections.observableArrayList();

	ObservableList<String> mynamelist = FXCollections.observableArrayList();

	TableView<OrderData> tv = new TableView<OrderData>();
	TableColumn<OrderData, Number> tv_id = new TableColumn<OrderData, Number>("���");
	TableColumn<OrderData, String> tv_orderId = new TableColumn<OrderData, String>("�������");
	TableColumn<OrderData, Number> tv_price = new TableColumn<OrderData, Number>("�۸�");
	TableColumn<OrderData, Number> tv_brokerage = new TableColumn<OrderData, Number>("Ӷ��");
	TextArea ta = new TextArea();










	@Override
	public void start(Stage primaryStage) throws Exception {





		//���洦��==================================================================
		HBox hbox0 =new HBox(10.0);
		VBox vbo = new VBox(10);
		VBox vb1 = new VBox(10);
		Tooltip tip1=new Tooltip();
		tip1.setText("��ϵ���߻�ȡ����˺���Ϣ");//����Tooltip����
		tip1.setFont(new Font("Arial", 16));//����Tooltip��������
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
		tip.setText("΢��ɨ���ά�룬��ע���ںţ���ϵ���߻�ȡ΢����Ϣ���ѷɸ����");//����Tooltip����
		tip.setFont(new Font("Arial", 16));//����Tooltip��������
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
		Tooltip tt4 = new Tooltip("��������߲��������õõļ۸������");
		tt4.setFont(new Font("Arial", 16));
		t_price.setTooltip(tt4);
		Tooltip tt5 = new Tooltip("��Ӷ����Ͳ��������õļ۸������");
		tt5.setFont(new Font("Arial", 16));
		t_bro.setTooltip(tt5);
		Tooltip tt6 = new Tooltip("ˢ����������ļ��ʱ��(����),��ʽ:1000-6000");
		tt6.setFont(new Font("Arial", 16));
		t_reftime.setTooltip(tt6);
		Tooltip tt7 = new Tooltip("΢��ɨ���ϽǶ�ά��,��ȡ���룬����");
		tt7.setFont(new Font("Arial", 16));
		t_ifeige.setTooltip(tt7);
		t_price.setPromptText("��������߲��������õõļ۸������");
		t_bro.setPromptText("��Ӷ����Ͳ��������õļ۸������");
		t_reftime.setPromptText("ˢ��������ʱ��(����),��ʽ:1000-6000");
		t_ifeige.setPromptText("�ɸ����");
		t_price.setPrefWidth(80);
		t_bro.setPrefWidth(80);
		t_ifeige.setPrefWidth(80);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(l_price,t_price,l_bro,t_bro,l_ifeige,t_ifeige,l_reftime,t_reftime);

		HBox hbox3 =new HBox(10.0);
		Tooltip tt1 =new Tooltip("ѡ�������ɹ�����������");
		tt1.setFont(new Font("Arial", 16));
		c_voice.setTooltip(tt1);
		Tooltip tt2 =new Tooltip("ѡ����������Ӷ�� ����");
		tt2.setFont(new Font("Arial", 16));
		c_voice1.setTooltip(tt2);
		Tooltip tt3 =new Tooltip("ѡ�񣬻����Ƿ������������");
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
		//�����ʼ�����==================================================================

		//����Ϊ����״̬��ʼ��=======================================================================
		b_login.setDisable(true);
		b_order.setDisable(true);
		b_pause.setDisable(true);
		//����Ϊ����״̬��ʼ�����=======================================================================

		//����Ϊ���ط����ʼ��=======================================================================
		LoginTdService lts = new LoginTdService();
		OrderService os = new OrderService();




		//����Ϊ��������=======================================================================
		b_login.setOnAction(new EventHandler<ActionEvent>() {
			//�˹���¼ƽ̨��ȡcookies
			@Override
			public void handle(ActionEvent event) {
				MyUtil.setGlobalPropertyG("account", "��ɭ5270");
				if(MyUtil.getLocalCookies()){
					print(MyUtil.getGlobalPropertyG("Cookies"));
					//TODO �������������ť2 ����һ������
					os.start();
					return;
				}
				Alert al = new Alert(AlertType.INFORMATION);
				al.setContentText("���ڵ�����������е�¼�Ե��˺ţ���¼��ȴ�����Զ�����");
				al.show();
				lts.start();
			}
		});


		//����Ϊservice�ļ���=========================================================================
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
						//TODO �������������ť1 ����һ������

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
