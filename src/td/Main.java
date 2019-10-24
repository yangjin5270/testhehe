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

		AnchorPane ap = new AnchorPane();
		ap.getChildren().addAll(bLoginTD);












		Scene scene = new Scene(ap,590,960);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/img/icon.png"));
		primaryStage.setTitle("TDFast...1.0.2");
		primaryStage.setScene(scene);
		primaryStage.show();

		//����Ϊ���ݳ�ʼ��=======================================================================
		//����Ϊ���ط����ʼ��=======================================================================
		LoginTdService lts = new LoginTdService();
		OrderService os = new OrderService();




		//����Ϊ��������=======================================================================
		bLoginTD.setOnAction(new EventHandler<ActionEvent>() {
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

	public void pintToA(Object o){
		if(o==null){return;}
		System.out.println(o.toString());
	}



}
